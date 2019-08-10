package com.example.myapplication.ui

import com.bumptech.glide.Glide

import com.example.myapplication.R
import com.example.myapplication.data.db.entity.ITunesResult
import com.example.myapplication.ui.utils.largerImage
import com.example.myapplication.ui.utils.priceFormat

import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item

import kotlinx.android.synthetic.main.recycler_view_item.*
import kotlinx.android.synthetic.main.recycler_view_item.view.*

class RecyclerContentItem(
    val iTunesResult: ITunesResult
): Item() {
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.apply {
            textView_title.text = iTunesResult.trackName
            textView_genre.text = iTunesResult.primaryGenreName
            textView_price.text = iTunesResult.trackPrice.priceFormat()
            Glide.with(itemView.context)
                .load(iTunesResult.artworkUrl100.largerImage(200))
                .into(itemView.imageView_Art)
        }
    }

    override fun getLayout() = R.layout.recycler_view_item

    override fun getSpanSize(spanCount: Int, position: Int) = spanCount/3
}