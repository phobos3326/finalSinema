package com.example.skillsinema.data.repository


import com.example.skillsinema.data.api.KinopoiskApi
import com.example.skillsinema.domain.model.*
import com.example.skillsinema.domain.repository.FilmRepository
import javax.inject.Inject

class FilmRepositoryImpl @Inject constructor(
    private val api: KinopoiskApi
) : FilmRepository {

    // ✅ Получение фильтров (жанры и страны)
    override suspend fun getFilters(): FiltersResponse {
        val response = api.getFilters()

        return if (response.isSuccessful) {
            response.body() ?: throw Exception("Empty filters response")
        } else {
            throw Exception("Failed to load filters: ${response.code()} ${response.message()}")
        }
    }

    // ✅ Получение фильтрованных фильмов
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

        return if (response.isSuccessful) {
            response.body()?.items ?: emptyList()
        } else {
            throw Exception("Failed to load filtered films: ${response.code()} ${response.message()}")
        }
    }

    // ✅ Получение премьер
    override suspend fun getPremieres(year: Int, month: String): List<Film> {
        val response = api.getPremieres(year, month)

        return if (response.isSuccessful) {
            response.body()?.items ?: emptyList()
        } else {
            throw Exception("Failed to load premieres: ${response.code()} ${response.message()}")
        }
    }

    // ✅ Получение топ 250 фильмов
    override suspend fun getTopFilms(): List<Film> {
        val response = api.getTopFilms(
            type = "TOP_250_BEST_FILMS",
            page = 1
        )

        return if (response.isSuccessful) {
            response.body()?.films ?: emptyList()
        } else {
            throw Exception("Failed to load top films: ${response.code()} ${response.message()}")
        }
    }

    // ✅ Получение сериалов
    override suspend fun getSerials(): List<Film> {
        val response = api.getFilteredFilms(
            countries = null,
            genres = null,
            order = "RATING",
            type = "TV_SERIES", // ← Тип: сериалы
            ratingFrom = 7,
            ratingTo = 10,
            yearFrom = 2000,
            yearTo = 2024,
            page = 1
        )

        return if (response.isSuccessful) {
            response.body()?.items ?: emptyList()
        } else {
            throw Exception("Failed to load serials: ${response.code()} ${response.message()}")
        }
    }

    // ✅ Получение деталей фильма по ID
    override suspend fun getFilmById(filmId: Int): FilmDetails {
        val response = api.getFilmById(filmId)

        return if (response.isSuccessful) {
            response.body() ?: throw Exception("Film not found")
        } else {
            throw Exception("Failed to load film details: ${response.code()} ${response.message()}")
        }
    }

    // ✅ Поиск фильмов
    override suspend fun searchFilms(query: String, page: Int): List<Film> {
        val response = api.searchFilms(query, page)

        return if (response.isSuccessful) {
            response.body()?.films ?: emptyList()
        } else {
            throw Exception("Failed to search films: ${response.code()} ${response.message()}")
        }
    }

    // ✅ Получение похожих фильмов
    override suspend fun getSimilarFilms(filmId: Int): List<Film> {
        val response = api.getSimilarFilms(filmId)

        return if (response.isSuccessful) {
            response.body()?.items ?: emptyList()
        } else {
            throw Exception("Failed to load similar films: ${response.code()} ${response.message()}")
        }
    }
}
