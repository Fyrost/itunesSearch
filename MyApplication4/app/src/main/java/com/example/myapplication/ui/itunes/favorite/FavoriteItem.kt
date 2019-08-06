package com.example.myapplication.ui.itunes.favorite

import com.example.myapplication.R
import com.example.myapplication.data.db.entity.ITunesResult
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.recycler_view_item.*

class FavoriteItem(
    val iTunesResult: ITunesResult
): Item() {
    override fun bind(viewHolder: ViewHolder, position: Int) {
        if (!iTunesResult.trackName.isNullOrBlank()) {
            viewHolder.apply {
                textView_title.text = iTunesResult.trackName
            }
        }
    }

    override fun getLayout() = R.layout.recycler_view_item
}