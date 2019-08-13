package com.example.myapplication.data.repository


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

import com.example.myapplication.data.db.entity.ITunesResult


interface ITunesRepository {
    val downloadedITunesResult: MutableLiveData<List<ITunesResult>>
    val inProgress: LiveData<Boolean>
    val noInternet: LiveData<Boolean>

    fun getResults(term: String?, media: String): LiveData<List<ITunesResult>>
}