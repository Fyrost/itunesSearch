package com.example.myapplication.ui


import android.view.Gravity
import android.view.ViewGroup.LayoutParams

import com.example.myapplication.R
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder

import kotlinx.android.synthetic.main.recycler_view_header.*


class RecyclerHeaderItem(private val message: String, private val isRecyclerEmpty: Boolean): Item() {
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.apply {
            if(isRecyclerEmpty) {
                header_container.apply {
                    layoutParams.height = LayoutParams.MATCH_PARENT
                    gravity = Gravity.CENTER
                }
                recycler_header.apply {
                    gravity = Gravity.CENTER
                    textSize = 22F
                }
            } else {
                header_container.layoutParams.height = LayoutParams.WRAP_CONTENT
                recycler_header.apply {
                    gravity = Gravity.START
                    textSize = 16F
                }
            }
            recycler_header.text = message
        }
    }

    override fun getLayout() = R.layout.recycler_view_header
}