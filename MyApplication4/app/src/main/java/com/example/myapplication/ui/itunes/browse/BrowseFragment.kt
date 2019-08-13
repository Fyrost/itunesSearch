package com.example.myapplication.ui.itunes.browse


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.lifecycle.ViewModelProviders
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager

import com.example.myapplication.R
import com.example.myapplication.data.db.entity.ITunesResult
import com.example.myapplication.databinding.BrowseFragmentBinding
import com.example.myapplication.ui.RecyclerContentItem
import com.example.myapplication.ui.RecyclerHeaderItem
import com.example.myapplication.ui.base.ScopeFragment
import com.example.myapplication.ui.utils.fabFilterAnimation
import com.example.myapplication.ui.utils.removeNull
import com.example.myapplication.ui.utils.toRecyclerContentItem

import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Section
import com.xwray.groupie.kotlinandroidextensions.ViewHolder

import kotlinx.android.synthetic.main.browse_fragment.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance


class BrowseFragment : ScopeFragment(), KodeinAware {
    override val kodein by closestKodein()
    private val viewModelFactory: BrowseViewModelFactory by instance()
    private var groupAdapter = GroupAdapter<ViewHolder>()
    private var filteredITunesResult: List<ITunesResult> = listOf()
    private val section = Section()

    private lateinit var viewModel: BrowseViewModel
    private var job: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(BrowseViewModel::class.java)
        val binding  = DataBindingUtil.inflate<BrowseFragmentBinding>(inflater, R.layout.browse_fragment,container,false)
            .apply {
                lifecycleOwner = this@BrowseFragment
                viewmodel = viewModel
            }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindUI()
    }

    private fun bindUI() {
        viewModel.term.observe(this@BrowseFragment, Observer {
            if (it.isNullOrBlank()) return@Observer
            job?.cancel()
            job = launch {
                delay(500L)
                viewModel.fetchResult()
            }

        })

        initRecyclerView(filteredITunesResult.toRecyclerContentItem())
        viewModel.result.observe(this@BrowseFragment, Observer{ iTunesResult ->
            if (iTunesResult == null)return@Observer
            filteredITunesResult = iTunesResult.removeNull()
            updateItems(filteredITunesResult.toRecyclerContentItem())
        })

        viewModel.isInProgress.observe(this@BrowseFragment, Observer { visible ->
            viewModel.inProgress.postValue(visible)
        })

        viewModel.isToggled.observe(this@BrowseFragment, Observer {
            if (fab_filter_menu_browse.isOpened) {
                fab_filter_menu_browse.toggle(true)
            }
        })

        fabFilterAnimation(fab_filter_menu_browse)
    }

    private fun initRecyclerView(items: List<RecyclerContentItem>) {
        groupAdapter = GroupAdapter<ViewHolder>().apply {
            spanCount = 3
        }

        browse_recyclerView.apply {
            layoutManager = GridLayoutManager(this@BrowseFragment.context, groupAdapter.spanCount). apply {
                spanSizeLookup = groupAdapter.spanSizeLookup
            }
            adapter = groupAdapter
        }

        section.apply {
            setHeader(RecyclerHeaderItem("Browse and Find medias at the search bar", items.isEmpty()))
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

    private fun updateItems(items: List<RecyclerContentItem>) {
        val message = if(items.isEmpty())  "Sorry, we couldn't find any media for \"${viewModel.lastTerm}\"" else "Results for \"${viewModel.lastTerm}\""
        section.setHeader(RecyclerHeaderItem(message,items.isEmpty()))
        section.update(items)
    }

    private fun navigateToDescription(iTunesResult: ITunesResult, view: View) {
        val actionDetail = BrowseFragmentDirections.actionDetail1(iTunesResult)
        Navigation.findNavController(view).navigate(actionDetail)
    }
}