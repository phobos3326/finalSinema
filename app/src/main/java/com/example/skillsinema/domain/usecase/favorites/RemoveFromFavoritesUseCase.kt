package com.example.skillsinema.domain.usecase.favorites

import com.example.skillsinema.domain.repository.DatabaseRepository
import javax.inject.Inject

class RemoveFromFavoritesUseCase @Inject constructor(
    private val databaseRepository: DatabaseRepository
) {
    suspend operator fun invoke(filmId: Int) {
        databaseRepository.removeFilmFromFavorites(filmId)
    }
}