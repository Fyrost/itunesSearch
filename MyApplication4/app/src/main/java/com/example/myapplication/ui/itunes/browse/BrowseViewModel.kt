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

    val noInternet: LiveData<Boolean> = iTunesRepository.noInternet

    private var media: String = "movie"

    val result: LiveData<List<ITunesResult>> = iTunesRepository.downloadedITunesResult

    var lastTerm: String? = ""
    var lastMedia: String? = ""
    var isToggled: MutableLiveData<Boolean> = MutableLiveData(true)

    init {
        inProgress.postValue(false)
    }

    fun fetchResult() {
        var searchTerm: String? = term.value
        if (lastTerm != searchTerm || lastMedia != media) {
            if (!searchTerm.isNullOrBlank()) {
                iTunesRepository.getResults(searchTerm, media)
            }
            lastTerm = searchTerm
            lastMedia = media
        }
    }

    val displayMedia
        get() = when (media) {
            "tvShow" -> "Tv Show"
            "musicVideo" -> "Music Video"
            else -> media.capitalize()
        }

    fun onclick(v: View) {
        media = v.tag.toString()
        isToggled.postValue(false)
        fetchResult()
    }
}


