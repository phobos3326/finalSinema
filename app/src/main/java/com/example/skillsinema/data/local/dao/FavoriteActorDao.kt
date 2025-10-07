package com.example.skillsinema.data.local.dao

import androidx.room.*
import com.example.skillsinema.data.local.entity.FavoriteActorEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteActorDao {

    @Query("SELECT * FROM favorite_actors ORDER BY addedAt DESC")
    fun getAllFavoriteActors(): Flow<List<FavoriteActorEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteActor(actor: FavoriteActorEntity)

    @Query("DELETE FROM favorite_actors WHERE personId = :actorId")
    suspend fun deleteFavoriteActor(actorId: Int)

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_actors WHERE personId = :actorId)")
    suspend fun isFavoriteActor(actorId: Int): Boolean
}