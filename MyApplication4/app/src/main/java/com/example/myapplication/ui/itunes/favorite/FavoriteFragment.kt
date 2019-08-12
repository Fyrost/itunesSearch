package com.example.myapplication.ui.itunes.favorite


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager

import com.example.myapplication.R
import com.example.myapplication.data.db.entity.ITunesResult
import com.example.myapplication.databinding.FavoriteFragmentBinding
import com.example.myapplication.ui.RecyclerContentItem
import com.example.myapplication.ui.RecyclerHeaderItem
import com.example.myapplication.ui.base.ScopeFragment
import com.example.myapplication.ui.utils.fabFilterAnimation
import com.example.myapplication.ui.utils.toRecyclerContentItem

import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Section
import com.xwray.groupie.kotlinandroidextensions.ViewHolder

import kotlinx.android.synthetic.main.favorite_fragment.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class FavoriteFragment : ScopeFragment(), KodeinAware {
    override val kodein by closestKodein()
    private val viewModelFactory: FavoriteViewModelFactory by instance()
    private var groupAdapter = GroupAdapter<ViewHolder>()
    private var favoriteResultList: List<ITunesResult> = listOf()
    private val section = Section()

    private lateinit var viewModel: FavoriteViewModel
    var job: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(FavoriteViewModel::class.java)
        val binding  = DataBindingUtil.inflate<FavoriteFragmentBinding>(inflater, R.layout.favorite_fragment,container,false)
            .apply {
                lifecycleOwner = this@FavoriteFragment
                viewmodel = viewModel
            }
        return binding.root
    }

    override fun onViewCreated(view: View,savedInstanceState: Bundle?) {
        super.onViewCreated(view,savedInstanceState)

        bindUI()
    }

    private fun bindUI() {
        viewModel.term.observe(this, Observer {
            if (it.isNullOrBlank()) return@Observer
            job?.cancel()
            job = launch {
                delay(500L)
                viewModel.fetchFavorites()
            }

        })

        viewModel.result.observe(this, Observer { iTunesResult ->
            if (iTunesResult == null)return@Observer
            favoriteResultList = iTunesResult
            updateItems(favoriteResultList.toRecyclerContentItem())
        })

        viewModel.dataChanged.observe(this@FavoriteFragment, Observer { dataChanged ->
            if (dataChanged) {
                viewModel.fetchFavorites()
            }
        })

        viewModel.isToggled.observe(this@FavoriteFragment, Observer {
            if (fab_filter_menu_favorite.isOpened) {
                fab_filter_menu_favorite.toggle(true)
            }
        })

        initRecyclerView(favoriteResultList.toRecyclerContentItem())

        fabFilterAnimation(fab_filter_menu_favorite)
    }

    private fun initRecyclerView(items: List<RecyclerContentItem>) {
        groupAdapter.apply {
            spanCount = 3
        }

        favorite_recyclerView.apply {
            layoutManager = GridLayoutManager(this@FavoriteFragment.context, groupAdapter.spanCount).apply {
                spanSizeLookup = groupAdapter.spanSizeLookup
            }
            adapter = groupAdapter
        }

        section.apply {
            setHeader(RecyclerHeaderItem("No ${viewModel.media} in your favorites yet", items.isEmpty()))
            addAll(items)
        }

        groupAdapter.apply {
            add(section)
            setOnItemClickListener{ item, view ->
                (item as? RecyclerContentItem)?.let {
                    navigateToDescription(it.iTunesResult, view)
                }
            }
        }
    }

    private fun navigateToDescription(iTunesResult: ITunesResult, view: View) {
        val actionDetail = FavoriteFragmentDirections.actionDetail(iTunesResult)
        Navigation.findNavController(view).navigate(actionDetail)
    }

    private fun updateItems(items: List<RecyclerContentItem>) {
        val message = if(items.isEmpty())  "No ${viewModel.media} in your favorites yet" else "Results for \"${viewModel.media}\""
        section.setHeader(RecyclerHeaderItem(message,items.isEmpty()))
        section.update(items)
    }
}
