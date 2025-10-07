package com.example.skillsinema.domain.usecase.favorites

import com.example.skillsinema.domain.model.Actor
import com.example.skillsinema.domain.repository.DatabaseRepository
import javax.inject.Inject

class SaveActorToFavoritesUseCase @Inject constructor(
    private val databaseRepository: DatabaseRepository
) {
    suspend operator fun invoke(actor: Actor) {
        databaseRepository.saveActorToFavorites(actor)
    }
}