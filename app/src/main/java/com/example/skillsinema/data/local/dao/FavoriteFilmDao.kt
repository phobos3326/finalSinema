package com.example.skillsinema.data.local.dao


import androidx.room.*
import com.example.skillsinema.data.local.entity.FavoriteFilmEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteFilmDao {

    @Query("SELECT * FROM favorite_films ORDER BY addedAt DESC")
    fun getAllFavorites(): Flow<List<FavoriteFilmEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(film: FavoriteFilmEntity)

    @Query("DELETE FROM favorite_films WHERE kinopoiskId = :filmId")
    suspend fun deleteFavorite(filmId: Int)

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_films WHERE kinopoiskId = :filmId)")
    suspend fun isFavorite(filmId: Int): Boolean

    @Query("SELECT COUNT(*) FROM favorite_films")
    suspend fun getFavoritesCount(): Int
}