package com.example.myapplication.data.repository


import androidx.lifecycle.LiveData

import com.example.myapplication.data.db.entity.ITunesResult


interface DatabaseRepository {
    val results: LiveData<List<ITunesResult>>
    val isDuplicate: LiveData<Boolean>
    val inProgress: LiveData<Boolean>

    fun getAll()
    fun insertResult(iTunesResult: ITunesResult)
    fun deleteResult(iTunesResult: ITunesResult)
    fun getMediaTermResult(term: String, media: String)
    fun getMediaResult(media: String)
    fun isDuplicate(id: Int)
}