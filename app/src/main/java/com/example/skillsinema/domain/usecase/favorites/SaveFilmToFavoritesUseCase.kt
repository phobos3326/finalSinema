package com.example.skillsinema.domain.usecase.favorites

import com.example.skillsinema.domain.model.Film
import com.example.skillsinema.domain.repository.DatabaseRepository
import javax.inject.Inject

class SaveFilmToFavoritesUseCase @Inject constructor(
    private val databaseRepository: DatabaseRepository
) {
    suspend operator fun invoke(film: Film) {
        databaseRepository.saveFilmToFavorites(film)
    }
}
