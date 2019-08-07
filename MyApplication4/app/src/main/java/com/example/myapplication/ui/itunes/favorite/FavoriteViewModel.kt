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

    init {
        databaseRepository.getAll()
    }

    @Bindable
    var term = MutableLiveData<String>()

    var media: String = "movie"

    var result: LiveData<List<ITunesResult>> = databaseRepository.results

    fun fetchFavorites() {
        if (term.value.isNullOrBlank()) {
            databaseRepository.getMediaResult(media)
        } else {
            databaseRepository.getMediaTermResult(term.value.toString(), media)
        }
    }

    fun onclick(v: View) {
        media = v.tag.toString()
        fetchFavorites()
    }
}
