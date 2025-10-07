package com.example.skillsinema.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_actors")
data class FavoriteActorEntity(
    @PrimaryKey val personId: Int,
    val nameRu: String?,
    val nameEn: String?,
    val posterUrl: String?,
    val profession: String?,
    val addedAt: Long = System.currentTimeMillis()
)