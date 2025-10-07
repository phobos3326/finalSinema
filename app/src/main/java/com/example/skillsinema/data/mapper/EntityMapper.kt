package com.example.skillsinema.data.mapper


import com.example.skillsinema.data.local.entity.*
import com.example.skillsinema.domain.model.*
import java.util.Collections.emptyList
import javax.inject.Inject

class EntityMapper @Inject constructor() {

    fun filmToFavoriteEntity(film: Film): FavoriteFilmEntity {
        return FavoriteFilmEntity(
            kinopoiskId = film.kinopoiskId,
            nameRu = film.nameRu,
            nameEn = film.nameEn,
            year = film.year,
            posterUrl = film.posterUrl,
            posterUrlPreview = film.posterUrlPreview,
            genres = film.genres,
            countries = film.countries,
            rating = film.rating
        )
    }

    fun favoriteEntityToFilm(entity: FavoriteFilmEntity): Film {
        return Film(
            kinopoiskId = entity.kinopoiskId,
            nameRu = entity.nameRu,
            nameEn = entity.nameEn,
            year = entity.year,
            posterUrl = entity.posterUrl,
            posterUrlPreview = entity.posterUrlPreview,
            genres = entity.genres,
            countries = entity.countries,
            rating = entity.rating
        )
    }

    fun favoriteEntitiesToFilms(entities: List<FavoriteFilmEntity>): List<Film> {
        return entities.map { favoriteEntityToFilm(it) }
    }

    fun filmToViewedEntity(film: Film): ViewedFilmEntity {
        return ViewedFilmEntity(
            kinopoiskId = film.kinopoiskId,
            nameRu = film.nameRu,
            nameEn = film.nameEn,
            year = film.year,
            posterUrl = film.posterUrl,
            posterUrlPreview = film.posterUrlPreview,
            genres = film.genres,
            countries = film.countries,
            rating = film.rating
        )
    }

    fun viewedEntityToFilm(entity: ViewedFilmEntity): Film {
        return Film(
            kinopoiskId = entity.kinopoiskId,
            nameRu = entity.nameRu,
            nameEn = entity.nameEn,
            year = entity.year,
            posterUrl = entity.posterUrl,
            posterUrlPreview = entity.posterUrlPreview,
            genres = entity.genres,
            countries = entity.countries,
            rating = entity.rating
        )
    }

    fun viewedEntitiesToFilms(entities: List<ViewedFilmEntity>): List<Film> {
        return entities.map { viewedEntityToFilm(it) }
    }

    fun actorToFavoriteEntity(actor: Actor): FavoriteActorEntity {
        return FavoriteActorEntity(
            personId = actor.personId,
            nameRu = actor.nameRu,
            nameEn = actor.nameEn,
            posterUrl = actor.posterUrl,
            profession = actor.profession
        )
    }

    fun favoriteActorEntityToActor(entity: FavoriteActorEntity): Actor {
        return Actor(
            personId = entity.personId,
            nameRu = entity.nameRu,
            nameEn = entity.nameEn,
            sex = null,
            posterUrl = entity.posterUrl,
            growth = null,
            birthday = null,
            death = null,
            age = null,
            birthplace = null,
            deathplace = null,
            profession = entity.profession,
            facts = emptyList(),
            films = emptyList()
        )
    }

    fun favoriteActorEntitiesToActors(entities: List<FavoriteActorEntity>): List<Actor> {
        return entities.map { favoriteActorEntityToActor(it) }
    }
}