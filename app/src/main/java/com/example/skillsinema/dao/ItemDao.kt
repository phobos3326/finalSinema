package com.example.skillsinema.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

@Dao
interface ItemDao {
    @Insert(entity = ItemFilm::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: ItemFilm)

    @Update
    suspend fun update(item: ItemFilm)
}