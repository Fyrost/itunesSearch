package com.example.myapplication.data.repository

import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.example.myapplication.data.db.ITunesResultDao
import com.example.myapplication.data.db.entity.ITunesResult

class DatabaseRepositoryImpl(
    private val iTunesResultDao: ITunesResultDao
) : DatabaseRepository {
    private val allResults: LiveData<List<ITunesResult>> = iTunesResultDao.getAll()

    override fun insertResult(iTunesResult: ITunesResult) {
        AsyncTask.execute {
            iTunesResultDao.insert(iTunesResult)
        }
    }

    override fun getAllResults(): LiveData<List<ITunesResult>> {
        return allResults
    }

    override fun getResults(term: String, media: String): LiveData<List<ITunesResult>> {
        print(term)
        return iTunesResultDao.getResults("%$term%", media)
    }
}