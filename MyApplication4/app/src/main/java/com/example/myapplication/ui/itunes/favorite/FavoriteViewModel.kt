package com.example.myapplication.ui.itunes.favorite


import android.view.View

import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import com.example.myapplication.data.db.entity.ITunesResult
import com.example.myapplication.data.repository.DatabaseRepository
import com.example.myapplication.ui.utils.hideKeyboard


class FavoriteViewModel(
    private val databaseRepository: DatabaseRepository
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
    var inProgress = MutableLiveData<Int>()

    var isToggled: MutableLiveData<Boolean> = MutableLiveData(false)

    var media: String = "feature-movie"

    val displayMedia
    get() = when (media) {
        "feature-movie" -> "Movie"
        "song" -> "Music"
        "music-video" -> "Music Video"
        else -> "Tv Show"
    }

    var result: LiveData<List<ITunesResult>> = databaseRepository.results
    var dataChanged: LiveData<Boolean> = databaseRepository.dataChanged

    var lastTerm: String? = ""
    var lastMedia: String? = ""

    init {
        databaseRepository.getAll()
    }

    fun fetchFavorites() {
        if (dataChanged.value!! || (lastTerm != term.value || media != lastMedia)) {
            if (term.value.isNullOrBlank()) {
                databaseRepository.getMediaResult(media)
            } else {
                databaseRepository.getMediaTermResult(term.value.toString(), media)
            }
            lastTerm = term.value
            lastMedia = media
        }
    }

    fun onclick(v: View) {
        isToggled.postValue(false)
        media = v.tag.toString()
        fetchFavorites()
        v.hideKeyboard()
    }

    private fun setInProgress() {
        inProgress.postValue(View.VISIBLE)
    }

    fun setNotInProgress() {
        inProgress.postValue(View.GONE)
    }
}
