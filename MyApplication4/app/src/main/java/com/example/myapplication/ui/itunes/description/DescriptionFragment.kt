package com.example.myapplication.ui.itunes.description

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.myapplication.R
import kotlinx.android.synthetic.main.description_fragment.*

class DescriptionFragment : Fragment() {

    private lateinit var viewModel: DescriptionViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.description_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val safeArgs = arguments?.let { DescriptionFragmentArgs.fromBundle(it) }
        textView_description_title.text = safeArgs?.title

        viewModel = ViewModelProviders.of(this).get(DescriptionViewModel::class.java)
    }

}
