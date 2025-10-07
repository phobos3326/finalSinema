package com.example.skillsinema.domain.usecase.favorites

import com.example.skillsinema.domain.repository.DatabaseRepository
import javax.inject.Inject

class RemoveActorFromFavoritesUseCase @Inject constructor(
    private val databaseRepository: DatabaseRepository
) {
    suspend operator fun invoke(actorId: Int) {
        databaseRepository.removeActorFromFavorites(actorId)
    }
}