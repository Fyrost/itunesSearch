package com.example.myapplication.ui.itunes.description

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.data.repository.DatabaseRepository

@Suppress("UNCHECKED_CAST")
class DescriptionViewModelFactory(
    private val databaseRepository: DatabaseRepository,
    private val appContext: Context
): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DescriptionViewModel(databaseRepository,appContext) as T
    }
}