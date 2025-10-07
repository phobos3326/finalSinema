package com.example.skillsinema.domain.usecase.film

import com.example.skillsinema.domain.model.Film
import com.example.skillsinema.domain.repository.FilmRepository
import javax.inject.Inject

class GetPremiereFilmsUseCase @Inject constructor(
    private val filmRepository: FilmRepository
) {
    suspend operator fun invoke(year: Int, month: String): List<Film> {
        return filmRepository.getPremieres(year, month)
    }
}