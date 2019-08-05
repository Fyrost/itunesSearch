package com.example.myapplication.data.db.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.data.ITunesApiService
import com.example.myapplication.data.db.entity.ITunesResult
import com.example.myapplication.data.db.network.response.ITunesResponse
import com.example.myapplication.internal.NoConnectivityException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ITunesNetworkDataSourceImpl(
    private val iTunesApiService: ITunesApiService
) : ITunesNetworkDataSource {

    override val downloadedITunesResult: MutableLiveData<List<ITunesResult>> = MutableLiveData()

    override fun fetchResults(term: String, media: String) {
        try {
            val call = iTunesApiService.getResults(term, media)
            call.enqueue(object: Callback<ITunesResponse> {
                override fun onFailure(call: Call<ITunesResponse>, t: Throwable) {
                    Log.e("Connectivity", "No Internet Connection")
                }
                override fun onResponse(call: Call<ITunesResponse>, response: Response<ITunesResponse>) {
                    downloadedITunesResult.postValue(response.body()!!.iTunesResult)
                }
            })
        } catch (e: NoConnectivityException) {
            Log.e("Connectivity", "No Internet Connection")
        }
    }
}