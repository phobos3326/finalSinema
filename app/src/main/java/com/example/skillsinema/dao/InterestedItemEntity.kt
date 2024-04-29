package com.example.skillsinema.dao

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "InterestedItemTable")
data class InterestedItemEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "idItem")
    val idItem: Int,

    @ColumnInfo(name = "typeItem")
    val typeItem: String,

    @ColumnInfo(name = "nameRUItem")
    var nameRUItem: String?,

    @ColumnInfo(name = "nameENItem")
    var nameENItem: String?,

   /* @ColumnInfo(name = "posterItem")
    var posterItem: Bitmap?,*/

    @ColumnInfo(name = "ratingItem")
    var ratingItem: Int?
)