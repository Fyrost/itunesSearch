package com.example.myapplication.data.repository


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

import com.example.myapplication.data.db.ITunesResultDao
import com.example.myapplication.data.db.entity.ITunesResult

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class DatabaseRepositoryImpl(
    private val iTunesResultDao: ITunesResultDao
) : DatabaseRepository {
    private val _inProgress = MutableLiveData<Boolean>()
    override val inProgress: LiveData<Boolean>
        get() = _inProgress

    private val _results = MutableLiveData<List<ITunesResult>>()
    override val results: LiveData<List<ITunesResult>>
        get() = _results

    private val _isDuplicate = MutableLiveData<Boolean>()
    override val isDuplicate: LiveData<Boolean>
        get() = _isDuplicate

    private val _dataChanged: MutableLiveData<Boolean> = MutableLiveData(false)
    override val dataChanged: LiveData<Boolean>
        get() = _dataChanged

    override fun getAll() {
        GlobalScope.launch(Dispatchers.IO) {
            _results.postValue(iTunesResultDao.getAll())
        }
    }

    override fun insertResult(iTunesResult: ITunesResult) {
        GlobalScope.launch(Dispatchers.IO) {
            iTunesResultDao.insert(iTunesResult)
            _isDuplicate.postValue(true)
            _dataChanged.postValue(true)
        }
    }

    override fun deleteResult(iTunesResult: ITunesResult) {
        GlobalScope.launch(Dispatchers.IO) {
            iTunesResultDao.deleteResult(iTunesResult)
            _isDuplicate.postValue(false)
            _dataChanged.postValue(true)
        }
    }

    override fun getMediaResult(media: String) {
        GlobalScope.launch(Dispatchers.IO) {
            _results.postValue(iTunesResultDao.getMediaResult(media))
            _dataChanged.postValue(false)
        }
    }

    override fun getMediaTermResult(term: String, media: String) {
        GlobalScope.launch(Dispatchers.IO) {
            _results.postValue(iTunesResultDao.getMediaTermResult("%$term%", media))
            _dataChanged.postValue(false)
        }
    }

    override fun isDuplicate(id: Int) {
        GlobalScope.launch(Dispatchers.IO) {
            when (iTunesResultDao.getSingleResult(id)) {
                0 -> _isDuplicate.postValue(false)
                else -> _isDuplicate.postValue(true)
            }
        }
    }
}