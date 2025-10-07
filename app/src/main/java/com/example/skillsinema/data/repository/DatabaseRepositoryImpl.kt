package com.example.skillsinema.data.repository

import com.example.skillsinema.data.local.dao.*
import com.example.skillsinema.data.local.entity.*
import com.example.skillsinema.data.mapper.EntityMapper
import com.example.skillsinema.domain.model.*
import com.example.skillsinema.domain.repository.DatabaseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DatabaseRepositoryImpl @Inject constructor(
    private val favoriteFilmDao: FavoriteFilmDao,
    private val viewedFilmDao: ViewedFilmDao,
    private val favoriteActorDao: FavoriteActorDao,
    private val entityMapper: EntityMapper
) : DatabaseRepository {

    override suspend fun saveFilmToFavorites(film: Film) {
        val entity = entityMapper.filmToFavoriteEntity(film)
        favoriteFilmDao.insertFavorite(entity)
    }

    override suspend fun removeFilmFromFavorites(filmId: Int) {
        favoriteFilmDao.deleteFavorite(filmId)
    }

    override suspend fun getFavoriteFilms(): Flow<List<Film>> {
        return favoriteFilmDao.getAllFavorites().map { entities ->
            entityMapper.favoriteEntitiesToFilms(entities)
        }
    }

    override suspend fun isFilmFavorite(filmId: Int): Boolean {
        return favoriteFilmDao.isFavorite(filmId)
    }

    override suspend fun saveFilmToViewed(film: Film) {
        val entity = entityMapper.filmToViewedEntity(film)
        viewedFilmDao.insertViewed(entity)
    }

    override suspend fun getViewedFilms(): Flow<List<Film>> {
        return viewedFilmDao.getAllViewed().map { entities ->
            entityMapper.viewedEntitiesToFilms(entities)
        }
    }

    override suspend fun saveActorToFavorites(actor: Actor) {
        val entity = entityMapper.actorToFavoriteEntity(actor)
        favoriteActorDao.insertFavoriteActor(entity)
    }

    override suspend fun removeActorFromFavorites(actorId: Int) {
        favoriteActorDao.deleteFavoriteActor(actorId)
    }

    override suspend fun getFavoriteActors(): Flow<List<Actor>> {
        return favoriteActorDao.getAllFavoriteActors().map { entities ->
            entityMapper.favoriteActorEntitiesToActors(entities)
        }
    }
}