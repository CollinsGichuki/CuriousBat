package com.snilloc.curiousbat.repository

import androidx.annotation.WorkerThread
import com.snilloc.curiousbat.db.FaveActivityDatabase
import com.snilloc.curiousbat.model.RandomActivity
import kotlinx.coroutines.flow.Flow

class FaveActivityRepository(private val db: FaveActivityDatabase) {

    @WorkerThread
    suspend fun insertActivity(activity: RandomActivity) {
        db.favActivityDao().insertActivity(activity)
    }

    @WorkerThread
    suspend fun deleteActivity(activity: RandomActivity) {
        db.favActivityDao().deleteActivity(activity)
    }

    val getAllActivities : Flow<List<RandomActivity>> = db.favActivityDao().getAllRandomActivities()
}