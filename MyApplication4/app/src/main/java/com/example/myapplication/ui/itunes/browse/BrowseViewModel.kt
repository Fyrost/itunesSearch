package com.example.myapplication.ui.itunes.browse


import android.view.View
import androidx.databinding.*

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import com.example.myapplication.data.db.entity.ITunesResult
import com.example.myapplication.data.repository.ITunesRepository


class BrowseViewModel(
    private val iTunesRepository: ITunesRepository
) : ViewModel(), Observable {
    private val callbacks: PropertyChangeRegistry by lazy { PropertyChangeRegistry() }
    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback) {
        callbacks.add(callback)
    }
    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback) {
        callbacks.remove(callback)
    }

    @Bindable
    var term = MutableLiveData<String>()

    @Bindable
    val inProgress: MutableLiveData<Boolean> = MutableLiveData()
    val isInProgress: LiveData<Boolean> = iTunesRepository.inProgress

    private var media: String = "movie"

    private var _result=  MutableLiveData<List<ITunesResult>>()
    val result: LiveData<List<ITunesResult>>
        get() = _result

    init {
        inProgress.postValue(false)
    }

    fun fetchResult() {
        var searchTerm: String? = term.value.toString()
        if (!searchTerm.isNullOrBlank()) {
            _result.postValue(iTunesRepository.getResults(term.value.toString(), media).value)
        }
    }

    fun onclick(v: View) {
        media = v.tag.toString()
        fetchResult()
    }

    fun removeNull(iTunesResult: List<ITunesResult>): List<ITunesResult> {
        var tempResult: MutableList<ITunesResult> = mutableListOf()
        for (row in iTunesResult) {
            if (!row.trackName.isNullOrBlank() || row.trackId != 0) {
                tempResult.add(row)
            }
        }
        return tempResult.toList()
    }
}
