package com.example.myapplication.ui.itunes.description

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel;
import com.example.myapplication.data.db.entity.ITunesResult
import com.example.myapplication.data.repository.DatabaseRepository

class DescriptionViewModel(
    private val databaseRepository: DatabaseRepository
) : ViewModel() {

    val allResults: LiveData<List<ITunesResult>> = databaseRepository.getAllResults()

    fun insertResult(iTunesResult: ITunesResult) {
        databaseRepository.insertResult(iTunesResult)
    }
}
