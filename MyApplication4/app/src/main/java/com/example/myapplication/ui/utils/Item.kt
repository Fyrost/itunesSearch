package com.example.myapplication.ui.utils

import com.example.myapplication.data.db.entity.ITunesResult
import com.example.myapplication.ui.itunes.browse.BrowseItem
import com.example.myapplication.ui.itunes.favorite.FavoriteItem

fun List<ITunesResult>.toFavoriteItem(): List<FavoriteItem> {
    return this.map {
        FavoriteItem(it)
    }
}

fun List<ITunesResult>.toBrowseItems(): List<BrowseItem> {
    return this.map {
        BrowseItem(it)
    }
}



