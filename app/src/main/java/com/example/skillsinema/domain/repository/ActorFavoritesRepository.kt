package com.example.skillsinema.domain.repository

import com.example.skillsinema.domain.model.Actor
import kotlinx.coroutines.flow.Flow

interface ActorFavoritesRepository {
    suspend fun saveActorToFavorites(actor: Actor)
    suspend fun removeActorFromFavorites(actorId: Int)
    suspend fun getFavoriteActors(): Flow<List<Actor>>
}
