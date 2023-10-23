package com.example.skillsinema.dao

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update
import javax.inject.Inject

class ItemRepository @Inject constructor(
private val itemDao: ItemDao
) {
    //@Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(itemFilm: ItemFilm) {
        itemDao.insert(itemFilm)
    }



   // @Update
    suspend fun update(itemFilm: ItemFilm){
        itemDao.update(itemFilm)
    }

    // Other repository functions
}