package com.example.skillsinema.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.skillsinema.domain.model.Genre
import com.example.skillsinema.domain.model.Country

@Entity(tableName = "favorite_films")
data class FavoriteFilmEntity(
    @PrimaryKey val kinopoiskId: Int,
    val nameRu: String?,
    val nameEn: String?,
    val year: String?,
    val posterUrl: String?,
    val posterUrlPreview: String?,
    val genres: List<Genre>,
    val countries: List<Country>,
    val rating: Double?,
    val addedAt: Long = System.currentTimeMillis()
)