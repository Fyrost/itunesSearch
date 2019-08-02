package com.example.myapplication.data.repository

import androidx.lifecycle.LiveData
import com.example.myapplication.data.db.ITunesResultDao
import com.example.myapplication.data.db.entity.ITunesResult
import com.example.myapplication.data.db.network.ITunesNetworkDataSource
import com.example.myapplication.data.db.network.response.ITunesResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.ZonedDateTime

class ITunesRepositoryImpl(
    private val iTunesNetworkDataSource: ITunesNetworkDataSource
) : ITunesRepository {

    override suspend fun getResults(term: String, media: String): LiveData<List<ITunesResult>> {
        iTunesNetworkDataSource.fetchResults(term, media)
        return iTunesNetworkDataSource.downloadedITunesResult
    }
}