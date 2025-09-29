package com.example.skillsinema.domain.usecase

import com.example.skillsinema.domain.model.Movie
import com.example.skillsinema.domain.repository.MovieRepository

class GetMovieDetails(private val repo: MovieRepository) {
    suspend operator fun invoke(id: Long): Result<Movie> = repo.details(id)
}