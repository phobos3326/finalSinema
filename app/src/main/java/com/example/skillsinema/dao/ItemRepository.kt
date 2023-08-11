package com.example.skillsinema.dao

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import javax.inject.Inject

class ItemRepository @Inject constructor(
private val itemDao: ItemDao
) {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(itemFilm: ItemFilm) {
        itemDao.insert(itemFilm)
    }

    // Other repository functions
}