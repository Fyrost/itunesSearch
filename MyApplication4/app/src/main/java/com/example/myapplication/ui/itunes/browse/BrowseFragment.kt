package com.example.myapplication.ui.itunes.browse

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.OvershootInterpolator
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.myapplication.R
import com.example.myapplication.data.db.entity.ITunesResult
import com.example.myapplication.databinding.BrowseFragmentBinding
import com.example.myapplication.ui.base.ScopeFragment
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.browse_fragment.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance
import java.util.*
import kotlin.concurrent.schedule

class BrowseFragment : ScopeFragment(), KodeinAware {
    override val kodein by closestKodein()
    private val viewModelFactory: BrowseViewModelFactory by instance()
    private var groupAdapter = GroupAdapter<ViewHolder>()
    private var filteredITunesResult: List<ITunesResult> = listOf()

    private lateinit var viewModel: BrowseViewModel
    private var timer: Timer? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(BrowseViewModel::class.java)
        val binding  = DataBindingUtil.inflate<BrowseFragmentBinding>(inflater,R.layout.browse_fragment,container,false)
            .apply {
                this.lifecycleOwner = this@BrowseFragment
                this.viewmodel = viewModel
            }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(BrowseViewModel::class.java)

        bindUI()
        fabFilterAnimation()
    }

    private fun bindUI() {

        viewModel.term.observe(this@BrowseFragment, Observer {
            if(timer != null){
                timer!!.cancel()
            }

            timer = Timer()
            timer!!.schedule(300L) {
                viewModel.fetchResult()
            }
        })

        initRecyclerView(filteredITunesResult.toBrowseItems())
        viewModel.fetchResult()
        viewModel.result.observe(this@BrowseFragment, Observer { iTunesResult ->
            if (iTunesResult == null)return@Observer
            group_loading.visibility = View.GONE
            filteredITunesResult = viewModel.removeNull(iTunesResult)
            updateItems(filteredITunesResult.toBrowseItems())
        })
    }

    private fun initRecyclerView(items: List<BrowseItem>) {
        groupAdapter = GroupAdapter<ViewHolder>().apply {
            addAll(items)
        }

        browse_recyclerView.apply {
            layoutManager = LinearLayoutManager(this@BrowseFragment.context)
            adapter = groupAdapter
        }

        groupAdapter.setOnItemClickListener{ item, view ->
            (item as? BrowseItem)?.let {
                showResultDetail(it.iTunesResult.trackName, view)
            }
        }
    }

    private fun updateItems(items: List<BrowseItem>) {
        groupAdapter.update(items)
    }

    private fun showResultDetail(title: String?, view: View) {
        val actionDetail = BrowseFragmentDirections.actionDetail1(title!!)
        Navigation.findNavController(view).navigate(actionDetail)
    }

    private fun List<ITunesResult>.toBrowseItems(): List<BrowseItem> {
        return this.map {
            BrowseItem(it)
        }
    }

    private fun fabFilterAnimation() {
        val set = AnimatorSet()

        val scaleOutX: ObjectAnimator = ObjectAnimator.ofFloat(fab_filter_menu.menuIconView,"ScaleX", 1.0f, 0.2f)
        val scaleOutY: ObjectAnimator = ObjectAnimator.ofFloat(fab_filter_menu.menuIconView,"ScaleY", 1.0f, 0.2f)

        val scaleInX: ObjectAnimator = ObjectAnimator.ofFloat(fab_filter_menu.menuIconView,"ScaleX", 0.2f, 1.0f)
        val scaleInY: ObjectAnimator = ObjectAnimator.ofFloat(fab_filter_menu.menuIconView,"ScaleY", 0.2f, 1.0f)

        scaleOutX.duration = 50
        scaleOutY.duration = 50

        scaleInX.duration = 150
        scaleInY.duration = 150

        scaleInX.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator?) {
                fab_filter_menu.menuIconView.setImageResource(if (!fab_filter_menu.isOpened) R.drawable.ic_fab_filter_close else R.drawable.ic_fab_filter_list)
            }
        })

        set.play(scaleOutX).with(scaleOutY)
        set.play(scaleInX).with(scaleInY).after(scaleOutX)
        set.interpolator = OvershootInterpolator(2F)

        fab_filter_menu.iconToggleAnimatorSet = set
        fab_filter_menu.setClosedOnTouchOutside(true)
    }
}