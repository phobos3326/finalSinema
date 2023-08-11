package com.example.skillsinema.dao

import androidx.room.Dao
import androidx.room.Insert

@Dao
interface ItemDao {
    @Insert(entity = ItemFilm::class)
    suspend fun insert(item: ItemFilm)
}