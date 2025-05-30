package com.example.skillsinema.dao

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "wantToSee")
data class WantToSeeFilm(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "filmId")
    val id: Int = 0
)
