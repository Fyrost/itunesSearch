package com.example.myapplication.ui.itunes.favorite


import com.bumptech.glide.Glide

import com.example.myapplication.R
import com.example.myapplication.data.db.entity.ITunesResult

import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder

import kotlinx.android.synthetic.main.recycler_view_item.*
import kotlinx.android.synthetic.main.recycler_view_item.view.*


class FavoriteItem(
    val iTunesResult: ITunesResult
): Item() {
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.apply {
            textView_title.text = iTunesResult.trackName
            textView_genre.text = iTunesResult.primaryGenreName
            textView_price.text = iTunesResult.trackPrice.toString()
            Glide.with(itemView.context)
                .load(iTunesResult.artworkUrl100)
                .into(itemView.imageView_Art)
        }
    }

    override fun getLayout() = R.layout.recycler_view_item
}