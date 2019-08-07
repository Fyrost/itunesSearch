package com.example.myapplication.ui.itunes.browse

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.data.repository.ITunesRepository

@Suppress("UNCHECKED_CAST")
class BrowseViewModelFactory(
    private val iTunesRepository: ITunesRepository
):ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return BrowseViewModel(iTunesRepository) as T
    }
}