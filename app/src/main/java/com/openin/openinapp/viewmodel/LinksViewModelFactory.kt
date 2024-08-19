package com.openin.openinapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.openin.openinapp.repository.LinksRepository

class LinksViewModelFactory(private val repository: LinksRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LinksViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LinksViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}