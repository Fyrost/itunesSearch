package com.example.myapplication.data.repository


import android.util.Log

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

import com.example.myapplication.data.ITunesApiService
import com.example.myapplication.data.db.entity.ITunesResult
import com.example.myapplication.data.db.network.response.ITunesResponse
import com.example.myapplication.internal.NoConnectivityException

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ITunesRepositoryImpl(
    private val iTunesApiService: ITunesApiService
) : ITunesRepository {
    override val downloadedITunesResult: MutableLiveData<List<ITunesResult>> = MutableLiveData()

    private val _inProgress = MutableLiveData<Boolean>()
    override val inProgress: LiveData<Boolean>
        get() = _inProgress

    override fun getResults(term: String?, media: String): LiveData<List<ITunesResult>> {
        _inProgress.postValue(true)
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val call = iTunesApiService.getResults(term, media)
                call.enqueue(object : Callback<ITunesResponse> {
                    override fun onFailure(call: Call<ITunesResponse>, t: Throwable) {
                        Log.e("Connectivity", "No Internet Connection")
                        _inProgress.postValue(false)
                    }

                    override fun onResponse(call: Call<ITunesResponse>, response: Response<ITunesResponse>) {
                        downloadedITunesResult.postValue(response.body()!!.iTunesResult)
                        _inProgress.postValue(false)
                    }
                })
            } catch (e: NoConnectivityException) {
                Log.e("Connectivity", "No Internet Connection")
                _inProgress.postValue(true)
            }
        }
        return downloadedITunesResult
    }
}