package com.snilloc.curiousbat.db

import androidx.room.*
import com.snilloc.curiousbat.model.RandomActivity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavActivityDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertActivity(activity: RandomActivity)

    @Delete
    suspend fun deleteActivity(activity: RandomActivity)

    @Query("SELECT * FROM random_act_table ORDER BY id DESC")
    fun getAllRandomActivities() : Flow<List<RandomActivity>>
}