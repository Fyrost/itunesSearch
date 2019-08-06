package com.example.myapplication.ui.itunes.description

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer

import com.example.myapplication.R
import com.example.myapplication.data.db.entity.ITunesResult
import kotlinx.android.synthetic.main.description_fragment.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class DescriptionFragment : Fragment(), KodeinAware {
    override val kodein by closestKodein()
    private lateinit var viewModel: DescriptionViewModel
    private val viewModelFactory: DescriptionViewModelFactory by instance()
    private lateinit var iTunesResult: ITunesResult

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.description_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(DescriptionViewModel::class.java)

        val safeArgs = arguments?.let { DescriptionFragmentArgs.fromBundle(it) }
        iTunesResult = safeArgs?.iTunesResult!!
        bindUI()
    }

    private fun bindUI() {
        textView_description_title.text = iTunesResult.trackName

        fab.setOnClickListener {
            viewModel.insertResult(iTunesResult)
        }

        viewModel.allResults.observe(this@DescriptionFragment, Observer {
            println(it)
        })
    }

}
