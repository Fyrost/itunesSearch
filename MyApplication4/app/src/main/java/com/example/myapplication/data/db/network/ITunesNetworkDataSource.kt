package com.example.myapplication.data.db.network

import androidx.lifecycle.LiveData
import com.example.myapplication.data.db.entity.ITunesResult

interface ITunesNetworkDataSource {
    val downloadedITunesResult: LiveData<List<ITunesResult>>

    suspend fun fetchResults(
        term: String,
        media: String
    )
}