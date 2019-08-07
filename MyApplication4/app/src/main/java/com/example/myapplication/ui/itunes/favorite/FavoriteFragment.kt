package com.example.myapplication.ui.itunes.favorite

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.OvershootInterpolator
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager

import com.example.myapplication.R
import com.example.myapplication.data.db.entity.ITunesResult
import com.example.myapplication.databinding.FavoriteFragmentBinding
import com.example.myapplication.ui.utils.fabFilterAnimation
import com.example.myapplication.ui.utils.toFavoriteItem
import com.github.clans.fab.FloatingActionButton
import com.github.clans.fab.FloatingActionMenu
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.browse_fragment.*
import kotlinx.android.synthetic.main.favorite_fragment.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance
import java.util.*
import kotlin.concurrent.schedule

class FavoriteFragment : Fragment(), KodeinAware {
    override val kodein by closestKodein()
    private val viewModelFactory: FavoriteViewModelFactory by instance()
    private lateinit var viewModel: FavoriteViewModel
    private var groupAdapter = GroupAdapter<ViewHolder>()
    private var filteredITunesResult: List<ITunesResult> = listOf()

    private var timer: Timer? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(FavoriteViewModel::class.java)
        val binding  = DataBindingUtil.inflate<FavoriteFragmentBinding>(inflater, R.layout.favorite_fragment,container,false)
            .apply {
                this.lifecycleOwner = this@FavoriteFragment
                this.viewmodel = viewModel
            }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        bindUI()
    }

    private fun bindUI() {
        viewModel.term.observe(this, Observer {
            if (it.isNullOrBlank()) return@Observer
            if(timer != null) {
                timer!!.cancel()
            }
            timer = Timer()
            timer!!.schedule(300L) {
                viewModel.fetchFavorites()
            }
        })

        viewModel.result.observe(this, Observer { iTunesResult ->
            if (iTunesResult == null)return@Observer
            group_loading1.visibility = View.GONE
            filteredITunesResult = iTunesResult
            updateItems(filteredITunesResult.toFavoriteItem())
        })

        initRecyclerView(filteredITunesResult.toFavoriteItem())
        fabFilterAnimation(fab_filter_menu_favorite)
    }

    private fun initRecyclerView(items: List<FavoriteItem>) {
        groupAdapter.apply {
            addAll(items)
        }

        favorite_recyclerView.apply {
            layoutManager = GridLayoutManager(this@FavoriteFragment.context, 3)
            adapter = groupAdapter
        }

        groupAdapter.setOnItemClickListener{ item, view ->
            (item as? FavoriteItem)?.let {
                navigateToDescription(it.iTunesResult, view)
            }
        }
    }

    private fun navigateToDescription(iTunesResult: ITunesResult, view: View) {
        val actionDetail = FavoriteFragmentDirections.actionDetail(iTunesResult)
        Navigation.findNavController(view).navigate(actionDetail)
    }

    private fun updateItems(items: List<FavoriteItem>) {
        groupAdapter.update(items)
    }
}
