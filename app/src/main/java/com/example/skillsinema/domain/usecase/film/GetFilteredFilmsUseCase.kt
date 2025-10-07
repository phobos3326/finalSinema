package com.example.skillsinema.domain.usecase.film

import com.example.skillsinema.domain.model.Film
import com.example.skillsinema.domain.model.FilterParams
import com.example.skillsinema.domain.repository.FilmRepository
import javax.inject.Inject

class GetFilteredFilmsUseCase @Inject constructor(
    private val filmRepository: FilmRepository
) {
    suspend operator fun invoke(filterParams: FilterParams): List<Film> {
        return filmRepository.getFilteredFilms(filterParams)
    }
}