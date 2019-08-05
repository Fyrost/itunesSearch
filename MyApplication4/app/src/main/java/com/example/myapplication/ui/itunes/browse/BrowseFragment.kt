package com.example.myapplication.ui.itunes.browse

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
    }

    private fun bindUI() {
//        editText_search.setOnEditorActionListener { _, actionId, _ ->
//            if (actionId == EditorInfo.IME_ACTION_DONE) {
//                viewModel.fetchResult()
//                false
//            }
//            true
//        }


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
}