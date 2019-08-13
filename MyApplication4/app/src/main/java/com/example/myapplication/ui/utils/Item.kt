package com.example.myapplication.ui.utils


import com.example.myapplication.data.db.entity.ITunesResult
import com.example.myapplication.ui.RecyclerContentItem


fun List<ITunesResult>.toRecyclerContentItem(): List<RecyclerContentItem> {
    return this.map {
        RecyclerContentItem(it)
    }
}




