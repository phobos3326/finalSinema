package com.example.skillsinema.data.api

import com.example.skillsinema.data.dto.*
import retrofit2.Response
import retrofit2.http.*

interface KinopoiskApi {

    companion object {
        const val API_KEY = "c3252e89-6d89-480c-b5bd-7c97cdffdd5c"
    }

    @Headers("X-API-KEY: $API_KEY")
    @GET("films/premieres")
    suspend fun getPremieres(
        @Query("year") year: Int,
        @Query("month") month: String
    ): PremiereResponseDto

    @Headers("X-API-KEY: $API_KEY")
    @GET("films/{id}")
    suspend fun getFilmDetails(
        @Path("id") id: Int
    ): FilmDetailsDto

    @Headers("X-API-KEY: $API_KEY")
    @GET("films/top")
    suspend fun getTopFilms(
        @Query("type") type: String = "TOP_250_BEST_FILMS",
        @Query("page") page: Int = 1
    ): TopFilmsResponseDto

    @Headers("X-API-KEY: $API_KEY")
    @GET("films")
    suspend fun getFilteredFilms(
        @Query("page") page: Int,
        @Query("countries") countries: Int?,
        @Query("genres") genres: Int?,
        @Query("ratingFrom") ratingFrom: Int,
        @Query("ratingTo") ratingTo: Int,
        @Query("yearFrom") yearFrom: Int?,
        @Query("yearTo") yearTo: Int?,
        @Query("order") order: String?,
        @Query("type") type: String?,
        @Query("keyword") keyword: String?
    ): FilteredFilmsResponseDto

    @Headers("X-API-KEY: $API_KEY")
    @GET("films/{id}/similars")
    suspend fun getSimilarFilms(
        @Path("id") filmId: Int
    ): SimilarFilmsResponseDto

    @Headers("X-API-KEY: $API_KEY")
    @GET("films/filters")
    suspend fun getFilters(): Response<FiltersDto>
}