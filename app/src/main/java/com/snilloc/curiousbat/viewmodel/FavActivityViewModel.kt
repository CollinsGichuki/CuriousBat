package com.snilloc.curiousbat.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.snilloc.curiousbat.model.RandomActivity
import com.snilloc.curiousbat.repository.FaveActivityRepository
import kotlinx.coroutines.launch

class FavActivityViewModel(private val repository: FaveActivityRepository): ViewModel() {

    fun insertFavActivity(activity: RandomActivity) = viewModelScope.launch {
        repository.insertActivity(activity)
    }

    fun deleteFavActivity(activity: RandomActivity) = viewModelScope.launch {
        repository.deleteActivity(activity)
    }
    //All the activities is in form of Flow in the Repository, we convert it to LiveData
    val allFavActivities : LiveData<List<RandomActivity>> = repository.getAllActivities.asLiveData()
}