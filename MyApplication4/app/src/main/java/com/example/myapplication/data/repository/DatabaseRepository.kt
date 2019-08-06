package com.example.myapplication.data.repository

import androidx.lifecycle.LiveData
import com.example.myapplication.data.db.entity.ITunesResult

interface DatabaseRepository {
    val results: LiveData<List<ITunesResult>>

    fun getAll()
    fun insertResult(iTunesResult: ITunesResult)
    fun getMediaTermResult(term: String, media: String)
    fun getMediaResult(media: String)
}