package com.snilloc.curiousbat.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

//We use the same data class for both the Network Request and caching in db
@Parcelize
@Entity(tableName = "random_act_table")
data class RandomActivity(
    val activity: String,
    val type: String,
    val participants: Int,
    val price: Double,
    val link: String,
    val key: String,
    val accessibility: Double,
): Parcelable{
    //Ignore the id when passing the object via navArgs
    @IgnoredOnParcel
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
}