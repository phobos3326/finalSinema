package com.example.skillsinema.domain.repository

import com.example.skillsinema.domain.model.Film
import kotlinx.coroutines.flow.Flow

interface FavoriteFilmRepository {
    suspend fun saveFilmToFavorites(film: Film)
    suspend fun removeFilmFromFavorites(filmId: Int)
    suspend fun getFavoriteFilms(): Flow<List<Film>>
    suspend fun isFilmFavorite(filmId: Int): Boolean
}
