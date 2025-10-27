package com.example.skillsinema.data.repository

import com.example.skillsinema.data.api.KinopoiskApi
import com.example.skillsinema.data.mapper.FilmMapper
import com.example.skillsinema.data.mapper.FilterMapper
import com.example.skillsinema.data.model.ModelFilter
import com.example.skillsinema.domain.model.*
import com.example.skillsinema.domain.repository.FilmRepository

import retrofit2.Response
import javax.inject.Inject

class FilmRepositoryImpl @Inject constructor(
    private val api: KinopoiskApi,
    private val filmMapper: FilmMapper,
    private val filterMapper: FilterMapper
) : FilmRepository {

    override suspend fun getPremieres(year: Int, month: String): List<Film> {
        val response = api.getPremieres(year, month)
        return filmMapper.mapFilmList(response.items)
    }

    override suspend fun getFilmDetails(id: Int): FilmDetails {
        val response = api.getFilmDetails(id)
        return filmMapper.mapFilmDetails(response)
    }

    override suspend fun getTopFilms(): List<Film> {
        val response = api.getTopFilms()
        return filmMapper.mapFilmList(response.films)
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
            keyword = null
        )
        return filmMapper.mapFilmList(response.items)
    }

    override suspend fun getSimilarFilms(filmId: Int): List<Film> {
        TODO("Not yet implemented")
    }

    // ← ДОБАВИТЬ НЕДОСТАЮЩИЕ МЕТОДЫ:

    override suspend fun getSerials(): List<Film> {
        val response = api.getFilteredFilms(
            page = 1,
            countries = null,
            genres = null,
            ratingFrom = 0,
            ratingTo = 10,
            yearFrom = null,
            yearTo = null,
            order = null,
            type = "TV_SERIES",  // ← Фильтр для сериалов
            keyword = null
        )
        return filmMapper.mapFilmList(response.items)
    }

    override suspend fun getFilters(): Response<ModelFilter> {
        return try {
            val response = api.getFilters()
            if (response.isSuccessful && response.body() != null) {
                val mappedFilters = filterMapper.mapFiltersDto(response.body()!!)
                Response.success(mappedFilters)
            } else {
                Response.error(response.code(), response.errorBody()!!)
            }
        } catch (e: Exception) {
            Response.error(500, okhttp3.ResponseBody.create(null, e.message ?: "Unknown error"))
        }
    }
}
