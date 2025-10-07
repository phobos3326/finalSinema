package com.example.skillsinema.domain.usecase.favorites

import com.example.skillsinema.domain.model.Film
import com.example.skillsinema.domain.repository.DatabaseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoriteFilmsUseCase @Inject constructor(
    private val databaseRepository: DatabaseRepository
) {
    suspend operator fun invoke(): Flow<List<Film>> {
        return databaseRepository.getFavoriteFilms()
    }
}