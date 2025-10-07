package com.example.skillsinema.domain.usecase.favorites

import com.example.skillsinema.domain.repository.DatabaseRepository
import javax.inject.Inject

class IsFilmFavoriteUseCase @Inject constructor(
    private val databaseRepository: DatabaseRepository
) {
    suspend operator fun invoke(filmId: Int): Boolean {
        return databaseRepository.isFilmFavorite(filmId)
    }
}