package com.example.skillsinema.data.api

import com.example.skillsinema.data.dto.FilmDetailsDto
import com.example.skillsinema.data.dto.FiltersDto
import com.example.skillsinema.data.dto.PremiereResponseDto
import com.example.skillsinema.data.dto.SimilarFilmsResponseDto
import com.example.skillsinema.data.remote.response.*
import com.example.skillsinema.domain.model.FilmDetails
import com.example.skillsinema.domain.model.FiltersResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface KinopoiskApi {

    // ✅ Получение фильтров
    @GET("v2.2/films/filters")
    suspend fun getFilters(): Response<FiltersDto>

    // ✅ Получение фильмов с фильтрацией
    @GET("v2.2/films")
    suspend fun getFilteredFilms(
        @Query("countries") countries: String?,
        @Query("genres") genres: String?,
        @Query("order") order: String,
        @Query("type") type: String,
        @Query("ratingFrom") ratingFrom: Int,
        @Query("ratingTo") ratingTo: Int,
        @Query("yearFrom") yearFrom: Int,
        @Query("yearTo") yearTo: Int,
        @Query("page") page: Int
    ): Response<FilmsResponseDto>


    // ✅ Получение премьер
    @GET("v2.2/films/premieres")
    suspend fun getPremieres(
        @Query("year") year: Int,
        @Query("month") month: String
    ): Response<PremiereResponseDto>

    // ✅ Получение топ фильмов
    @GET("v2.2/films/top")
    suspend fun getTopFilms(
        @Query("type") type: String,
        @Query("page") page: Int
    ): Response<TopFilmsResponseDto>

    // ✅ Получение деталей фильма
    @GET("v2.2/films/{id}")
    suspend fun getFilmById(
        @Path("id") filmId: Int
    ): Response<FilmDetailsDto>

    // ✅ Поиск фильмов
    @GET("v2.1/films/search-by-keyword")
    suspend fun searchFilms(
        @Query("keyword") keyword: String,
        @Query("page") page: Int
    ): Response<SearchResponseDto>

    // ✅ Получение похожих фильмов
    @GET("v2.2/films/{id}/similars")
    suspend fun getSimilarFilms(
        @Path("id") filmId: Int
    ): Response<SimilarFilmsResponseDto>
}
