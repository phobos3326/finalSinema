package com.example.skillsinema.dao

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.skillsinema.ui.main.home.TypeItem

@Entity(tableName = "InterestedItemTable")
data class InterestedItemEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "idItem")
    val idItem: Int,

    @ColumnInfo(name = "typeItem")
    val typeItem: TypeItem,

    @ColumnInfo(name = "nameRUItem")
    var nameRUItem: String?,

    @ColumnInfo(name = "nameENItem")
    var nameENItem: String?,

    @ColumnInfo(name = "posterItem")
    var posterItem: ByteArray?,

    @ColumnInfo(name = "ratingItem")
    var ratingItem: Int?
)