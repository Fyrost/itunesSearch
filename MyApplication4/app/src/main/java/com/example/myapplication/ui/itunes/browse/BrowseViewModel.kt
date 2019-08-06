package com.example.myapplication.ui.itunes.browse

import android.view.View
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
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

    private var media: String = "music"

    var result: LiveData<List<ITunesResult>> = iTunesRepository.getResults(term.value.toString(), media)

    fun fetchResult() {
        result = iTunesRepository.getResults(term.value.toString(), media)
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
