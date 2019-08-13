package com.example.myapplication.ui.utils


import com.example.myapplication.data.db.entity.ITunesResult


fun List<ITunesResult>.removeNull(): List<ITunesResult> {
    var tempResult: MutableList<ITunesResult> = mutableListOf()
    if (this.isNotEmpty()) {
        this.forEach { row ->
            if (!row.trackName.isNullOrBlank() && row.trackId != 0 && row.trackPrice != null && row.trackPrice > 0) {
                tempResult.add(row)
            }
        }
    }
    return tempResult
}