package com.example.skillsinema.domain.repository

import com.example.skillsinema.domain.model.Film
import kotlinx.coroutines.flow.Flow

interface ViewedFilmRepository {
    suspend fun saveFilmToViewed(film: Film)
    suspend fun getViewedFilms(): Flow<List<Film>>
}
