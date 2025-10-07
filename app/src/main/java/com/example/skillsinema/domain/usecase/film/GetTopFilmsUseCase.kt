package com.example.skillsinema.domain.usecase.film

import com.example.skillsinema.domain.model.Film
import com.example.skillsinema.domain.repository.FilmRepository
import javax.inject.Inject

class GetTopFilmsUseCase @Inject constructor(
    private val filmRepository: FilmRepository
) {
    suspend operator fun invoke(): List<Film> {
        return filmRepository.getTopFilms()
    }
}