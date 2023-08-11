package com.example.skillsinema.dao

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "viewedItem")
data class ItemFilm(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "filmID")
    val id: Int? = 0

)
