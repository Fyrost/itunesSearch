package com.example.myapplication.ui.itunes.browse

import androidx.lifecycle.ViewModel
import com.example.myapplication.data.db.entity.ITunesResult
import com.example.myapplication.data.repository.ITunesRepository
import com.example.myapplication.internal.lazyDeferred

class BrowseViewModel(
    private val iTunesRepository: ITunesRepository
) : ViewModel() {
    var term: String = ""

    val media: String
        get() = "music"

    val result by lazyDeferred {
        iTunesRepository.getResults(term, media)
    }

    fun removeNull(iTunesResult: List<ITunesResult>): List<ITunesResult> {
        var tempResult: MutableList<ITunesResult> = mutableListOf()
        for (row in iTunesResult) {
            if (!row.trackName.isNullOrBlank() || row.trackId != 0) {
                tempResult.add(row)
            }
        }
        return tempResult.toList()
    }
}
