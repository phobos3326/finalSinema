package com.example.skillsinema.data.repository

import com.example.skillsinema.data.api.KinopoiskApi
import com.example.skillsinema.data.mapper.FilmMapper
import com.example.skillsinema.data.mapper.FiltersMapper
import com.example.skillsinema.domain.model.Film
import com.example.skillsinema.domain.model.FilmDetails
import com.example.skillsinema.domain.model.FilterParams
import com.example.skillsinema.domain.model.FiltersResponse
import com.example.skillsinema.domain.repository.FilmRepository
import javax.inject.Inject

class FilmRepositoryImpl @Inject constructor(
    private val api: KinopoiskApi,
    private val filmMapper: FilmMapper,
    private val filtersMapper: FiltersMapper
) : FilmRepository {

    override suspend fun getFilters(): FiltersResponse {
        val response = api.getFilters()
        if (!response.isSuccessful) {
            throw Exception("Failed to load filters: ${response.code()} ${response.message()}")
        }
        val dto = response.body() ?: throw Exception("Empty filters response")
        return filtersMapper.map(dto)
    }

    override suspend fun getFilteredFilms(filterParams: FilterParams): List<Film> {
        val response = api.getFilteredFilms(
            countries = filterParams.countries,
            genres = filterParams.genres,
            order = filterParams.order ?: "RATING",
            type = filterParams.type ?: "FILM",
            ratingFrom = filterParams.ratingFrom ?: 0,
            ratingTo = filterParams.ratingTo ?: 10,
            yearFrom = filterParams.yearFrom ?: 1000,
            yearTo = filterParams.yearTo ?: 3000,
            page = filterParams.page
        )

        if (!response.isSuccessful) {
            throw Exception("Failed to load filtered films: ${response.code()} ${response.message()}")
        }

        val itemsDto = response.body()?.items.orEmpty()
        return filmMapper.mapFilmList(itemsDto)
    }

    override suspend fun getPremieres(year: Int, month: String): List<Film> {
        val response = api.getPremieres(year, month)

        if (!response.isSuccessful) {
            throw Exception("Failed to load premieres: ${response.code()} ${response.message()}")
        }

        val itemsDto = response.body()?.items.orEmpty()
        return filmMapper.mapFilmList(itemsDto)
    }

    override suspend fun getTopFilms(): List<Film> {
        val response = api.getTopFilms(type = "TOP_250_BEST_FILMS", page = 1)

        if (!response.isSuccessful) {
            throw Exception("Failed to load top films: ${response.code()} ${response.message()}")
        }

        val filmsDto = response.body()?.films.orEmpty()     // List<FilmDto>
        return filmMapper.mapFilmList(filmsDto)             // List<Film>
    }


    override suspend fun getSerials(): List<Film> {
        val response = api.getFilteredFilms(
            countries = null,
            genres = null,
            order = "RATING",
            type = "TV_SERIES",
            ratingFrom = 7,
            ratingTo = 10,
            yearFrom = 2000,
            yearTo = 2024,
            page = 1
        )

        if (!response.isSuccessful) {
            throw Exception("Failed to load serials: ${response.code()} ${response.message()}")
        }

        val itemsDto = response.body()?.items.orEmpty()
        return filmMapper.mapFilmList(itemsDto)
    }

    override suspend fun getFilmById(filmId: Int): FilmDetails {
        val response = api.getFilmById(filmId)

        if (!response.isSuccessful) {
            throw Exception("Failed to load film details: ${response.code()} ${response.message()}")
        }

        val dto = response.body() ?: throw Exception("Film not found")
        return filmMapper.mapFilmDetails(dto)
    }

    override suspend fun searchFilms(query: String, page: Int): List<Film> {
        val response = api.searchFilms(keyword = query, page = page)

        if (!response.isSuccessful) {
            throw Exception("Failed to search films: ${response.code()} ${response.message()}")
        }

        val filmsDto = response.body()?.films.orEmpty()     // List<FilmDto>
        return filmMapper.mapFilmList(filmsDto)             // List<Film>
    }


    override suspend fun getSimilarFilms(filmId: Int): List<Film> {
        val response = api.getSimilarFilms(filmId)

        if (!response.isSuccessful) {
            throw Exception("Failed to load similar films: ${response.code()} ${response.message()}")
        }

        val itemsDto = response.body()?.items.orEmpty()
        return filmMapper.mapFilmList(itemsDto)
    }
}
