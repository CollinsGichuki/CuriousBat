package com.snilloc.curiousbat.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.snilloc.curiousbat.model.RandomActivity
import com.snilloc.curiousbat.network.RandomActivityAPIService
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers

class RandomActivityViewModel : ViewModel() {

    private val apiService = RandomActivityAPIService()
    private val compositeDisposable = CompositeDisposable()

    val loadRandomActivity = MutableLiveData<Boolean>()
    val randomActivityResponse = MutableLiveData<RandomActivity>()
    val randomActivityLoadError = MutableLiveData<Boolean>()

    fun getRandomActivityFromAPI() {
        loadRandomActivity.value = true
        compositeDisposable.add(
            apiService.getRandomActivity()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(
                    object : DisposableSingleObserver<RandomActivity>() {
                        override fun onSuccess(response: RandomActivity?) {
                            Log.d("TAG", "response success, $response)")
                            loadRandomActivity.value = false
                            randomActivityResponse.value = response
                            randomActivityLoadError.value = false
                        }

                        override fun onError(e: Throwable?) {
                            loadRandomActivity.value = false
                            randomActivityLoadError.value = false
                            Log.d("TAG", "Error fetching data, $e")
                            e!!.printStackTrace()
                        }
                    }
                )
        )
    }
}