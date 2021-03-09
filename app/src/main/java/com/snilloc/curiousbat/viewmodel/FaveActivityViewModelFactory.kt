package com.snilloc.curiousbat.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.snilloc.curiousbat.repository.FaveActivityRepository
import java.lang.IllegalArgumentException

@Suppress("UNCHECKED_CAST")
class FaveActivityViewModelFactory(
    private val repository: FaveActivityRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavActivityViewModel::class.java)) {
            return FavActivityViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}