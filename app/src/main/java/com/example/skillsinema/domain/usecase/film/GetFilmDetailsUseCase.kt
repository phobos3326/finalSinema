package com.example.skillsinema.domain.usecase.film

import com.example.skillsinema.domain.model.FilmDetails
import com.example.skillsinema.domain.repository.FilmRepository
import javax.inject.Inject

class GetFilmDetailsUseCase @Inject constructor(
    private val filmRepository: FilmRepository
) {
    suspend operator fun invoke(filmId: Int): FilmDetails {
        return filmRepository.getFilmById(filmId)
    }
}