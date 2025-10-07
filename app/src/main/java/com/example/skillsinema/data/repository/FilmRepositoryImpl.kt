package com.example.skillsinema.data.repository

import com.example.skillsinema.data.api.KinopoiskApi
import com.example.skillsinema.data.mapper.FilmMapper
import com.example.skillsinema.domain.model.*
import com.example.skillsinema.domain.repository.FilmRepository
import javax.inject.Inject

class FilmRepositoryImpl @Inject constructor(
    private val api: KinopoiskApi,
    private val mapper: FilmMapper
) : FilmRepository {

    override suspend fun getPremieres(year: Int, month: String): List<Film> {
        val response = api.getPremieres(year, month)
        return mapper.mapFilmList(response.items)
    }

    override suspend fun getFilmDetails(id: Int): FilmDetails {
        val response = api.getFilmDetails(id)
        return mapper.mapFilmDetails(response)
    }

    override suspend fun getTopFilms(): List<Film> {
        val response = api.getTopFilms()
        return mapper.mapFilmList(response.films)
    }

    override suspend fun getFilteredFilms(filterParams: FilterParams): List<Film> {
        val response = api.getFilteredFilms(
            page = filterParams.page,
            countries = filterParams.countries,
            genres = filterParams.genres,
            ratingFrom = filterParams.ratingFrom ?: 0,
            ratingTo = filterParams.ratingTo ?: 10,
            yearFrom = filterParams.yearFrom,
            yearTo = filterParams.yearTo,
            order = filterParams.order,
            type = filterParams.filmType,
            keyword = filterParams.keyword
        )
        return mapper.mapFilmList(response.items)
    }

    override suspend fun getSimilarFilms(filmId: Int): List<Film> {
        val response = api.getSimilarFilms(filmId)
        return mapper.mapFilmList(response.items)
    }
}