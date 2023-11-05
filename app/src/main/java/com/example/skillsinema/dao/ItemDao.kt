package com.example.skillsinema.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.skillsinema.entity.Film
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao {

    @Query("SELECT * FROM viewedItem")
    fun getAll(): List<ItemFilm>
    @Insert(entity = ItemFilm::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: ItemFilm)

    @Update
    suspend fun update(item: ItemFilm)
}



@Dao
interface LikedFilmDao{
    @Query("SELECT * FROM isLiked")
    fun getAll():List<LikedFilms>
    @Insert(entity = LikedFilms::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item:LikedFilms)
    @Update
    suspend fun update(item:LikedFilms)
    @Delete
    suspend fun delete(item:LikedFilms)
}