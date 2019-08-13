package com.example.myapplication.ui.itunes.favorite


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import com.example.myapplication.data.repository.DatabaseRepository


@Suppress("UNCHECKED_CAST")
class FavoriteViewModelFactory(
    private val databaseRepository: DatabaseRepository
): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FavoriteViewModel(databaseRepository) as T
    }
}