package com.example.skillsinema.domain.repository

import com.example.skillsinema.domain.model.Film

interface SearchRepository {
    suspend fun searchFilms(query: String, page: Int): List<Film>
}
