package com.example.skillsinema.domain.repository

import com.example.skillsinema.domain.model.Movie


interface MovieRepository {
    suspend fun popular(): Result<List<Movie>>
    suspend fun details(id: Long): Result<Movie>
}