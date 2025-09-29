package com.example.skillsinema.domain.usecase

import com.example.skillsinema.domain.model.Movie
import com.example.skillsinema.domain.repository.MovieRepository

class GetPopularMovies(private val repo: MovieRepository) {
    suspend operator fun invoke(): Result<List<Movie>> = repo.popular()
}