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

    private val _results = MutableLiveData<List<ITunesResult>>()
    override val results: LiveData<List<ITunesResult>>
        get() = _results

    override fun getAll() {
        GlobalScope.launch(Dispatchers.IO) {
            _results.postValue(iTunesResultDao.getAll())
        }
    }

    override fun insertResult(iTunesResult: ITunesResult) {
        GlobalScope.launch(Dispatchers.IO) {
            iTunesResultDao.insert(iTunesResult)
        }
    }

    override fun getMediaResult(media: String) {
        GlobalScope.launch(Dispatchers.IO) {
            println(iTunesResultDao.getMediaResult(media))
            _results.postValue(iTunesResultDao.getMediaResult(media))
        }
    }

    override fun getMediaTermResult(term: String, media: String) {
        GlobalScope.launch(Dispatchers.IO) {
            _results.postValue(iTunesResultDao.getMediaTermResult(term, media))
        }
    }
}