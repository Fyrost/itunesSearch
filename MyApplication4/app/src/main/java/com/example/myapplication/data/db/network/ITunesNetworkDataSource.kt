package com.example.myapplication.data.db.network

import androidx.lifecycle.MutableLiveData
import com.example.myapplication.data.db.entity.ITunesResult

interface ITunesNetworkDataSource {
    val downloadedITunesResult: MutableLiveData<List<ITunesResult>>

    fun fetchResults(
        term: String,
        media: String
    )
}