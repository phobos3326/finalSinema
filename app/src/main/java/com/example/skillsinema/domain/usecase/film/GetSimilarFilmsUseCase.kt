package com.example.skillsinema.domain.usecase.film

import com.example.skillsinema.domain.model.Film
import com.example.skillsinema.domain.repository.FilmRepository
import javax.inject.Inject

class GetSimilarFilmsUseCase @Inject constructor(
    private val filmRepository: FilmRepository
) {
    suspend operator fun invoke(filmId: Int): List<Film> {
        return filmRepository.getSimilarFilms(filmId)
    }
}