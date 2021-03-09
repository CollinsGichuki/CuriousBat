package com.snilloc.curiousbat.network

import com.snilloc.curiousbat.model.RandomActivity
import com.snilloc.curiousbat.utils.Constants
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface RandomActivityAPI {
    @GET(Constants.API_ENDPOINT)
    fun getRandomActivity() : Single<RandomActivity>
}