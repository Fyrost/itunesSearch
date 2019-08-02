package com.example.myapplication.data.db.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.data.ITunesApiService
import com.example.myapplication.data.db.entity.ITunesResult
import com.example.myapplication.data.db.network.response.ITunesResponse
import com.example.myapplication.internal.NoConnectivityException

class ITunesNetworkDataSourceImpl(
    private val iTunesApiService: ITunesApiService
) : ITunesNetworkDataSource {

    private val _downloadedITunesResult = MutableLiveData<List<ITunesResult>>()
    override val downloadedITunesResult: LiveData<List<ITunesResult>>
        get() = _downloadedITunesResult

    override suspend fun fetchResults(term: String, media: String) {
        try {
            val fetchedResults = iTunesApiService.getResults(term, media).await()
            _downloadedITunesResult.postValue(fetchedResults.iTunesResult)
        } catch (e: NoConnectivityException) {
            Log.e("Connectivity", "No Internet Connection")
        }
    }
}