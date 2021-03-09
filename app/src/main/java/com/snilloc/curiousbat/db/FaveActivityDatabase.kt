package com.snilloc.curiousbat.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.snilloc.curiousbat.model.RandomActivity

@Database(entities = [RandomActivity::class], version = 1)
abstract class FaveActivityDatabase : RoomDatabase() {

    abstract fun favActivityDao(): FavActivityDAO

    companion object {
        @Volatile
        private var INSTANCE: FaveActivityDatabase? = null

        fun getDatabase(context: Context): FaveActivityDatabase {
            //There should only be one DataBase instance at any time
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FaveActivityDatabase::class.java,
                    "random_act_db"
                ).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}