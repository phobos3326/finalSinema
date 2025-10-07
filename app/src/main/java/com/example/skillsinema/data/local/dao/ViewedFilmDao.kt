package com.example.skillsinema.data.local.dao

import androidx.room.*
import com.example.skillsinema.data.local.entity.ViewedFilmEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ViewedFilmDao {

    @Query("SELECT * FROM viewed_films ORDER BY viewedAt DESC")
    fun getAllViewed(): Flow<List<ViewedFilmEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertViewed(film: ViewedFilmEntity)

    @Query("DELETE FROM viewed_films WHERE kinopoiskId = :filmId")
    suspend fun deleteViewed(filmId: Int)

    @Query("SELECT EXISTS(SELECT 1 FROM viewed_films WHERE kinopoiskId = :filmId)")
    suspend fun isViewed(filmId: Int): Boolean

    @Query("DELETE FROM viewed_films")
    suspend fun clearAll()
}