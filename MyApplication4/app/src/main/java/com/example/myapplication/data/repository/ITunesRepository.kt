package com.example.myapplication.data.repository

import androidx.lifecycle.LiveData
import com.example.myapplication.data.db.entity.ITunesResult

interface ITunesRepository {
    fun getResults(term: String, media: String): LiveData<List<ITunesResult>>
}