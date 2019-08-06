package com.example.myapplication.data.repository

import androidx.lifecycle.LiveData
import com.example.myapplication.data.db.entity.ITunesResult
import com.example.myapplication.data.db.network.ITunesNetworkDataSource

class ITunesRepositoryImpl(
    private val iTunesNetworkDataSource: ITunesNetworkDataSource
) : ITunesRepository {

    override fun getResults(term: String, media: String): LiveData<List<ITunesResult>> {
        iTunesNetworkDataSource.fetchResults(term, media)
        return iTunesNetworkDataSource.downloadedITunesResult
    }
}