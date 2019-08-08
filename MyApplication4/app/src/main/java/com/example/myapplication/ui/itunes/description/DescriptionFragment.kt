package com.example.myapplication.ui.itunes.description


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.lifecycle.ViewModelProviders
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer

import com.example.myapplication.R
import com.example.myapplication.data.db.entity.ITunesResult
import com.example.myapplication.databinding.DescriptionFragmentBinding
import kotlinx.android.synthetic.main.description_fragment.*

import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

import kotlinx.android.synthetic.main.description_fragment.*


class DescriptionFragment : Fragment(), KodeinAware {
    override val kodein by closestKodein()
    private lateinit var viewModel: DescriptionViewModel
    private val viewModelFactory: DescriptionViewModelFactory by instance()
    private lateinit var iTunesResult: ITunesResult

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(DescriptionViewModel::class.java)
        val binding  = DataBindingUtil.inflate<DescriptionFragmentBinding>(inflater, R.layout.description_fragment,container,false)
            .apply {
                this.lifecycleOwner = this@DescriptionFragment
                this.viewmodel = viewModel
            }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val safeArgs = arguments?.let { DescriptionFragmentArgs.fromBundle(it) }
        iTunesResult = safeArgs?.iTunesResult!!

        bindUI()
    }

    private fun bindUI() {
        viewModel.detailDescription(iTunesResult)
    }

}
