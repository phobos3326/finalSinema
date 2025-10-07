package com.example.skillsinema.domain.repository
import com.example.skillsinema.domain.model.Actor
import com.example.skillsinema.domain.model.Film
import kotlinx.coroutines.flow.Flow

interface DatabaseRepository {
    suspend fun saveFilmToFavorites(film: Film)
    suspend fun removeFilmFromFavorites(filmId: Int)
    suspend fun getFavoriteFilms(): Flow<List<Film>>
    suspend fun isFilmFavorite(filmId: Int): Boolean

    suspend fun saveFilmToViewed(film: Film)
    suspend fun getViewedFilms(): Flow<List<Film>>

    suspend fun saveActorToFavorites(actor: Actor)
    suspend fun removeActorFromFavorites(actorId: Int)
    suspend fun getFavoriteActors(): Flow<List<Actor>>
}