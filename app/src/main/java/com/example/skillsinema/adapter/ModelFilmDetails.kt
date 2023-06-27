package com.example.skillsinema.adapter

import com.example.skillsinema.entity.FilmDTO

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)

    data class ModelFilmDetails (
        @Json(name = "completed")
        val completed: Boolean?,
        @Json(name = "countries")
        val countries: List<Country>?,
        @Json(name = "coverUrl")
        val coverUrl: String?,
        @Json(name = "description")
        val description: String?,
        @Json(name = "editorAnnotation")
        val editorAnnotation: String?,
        @Json(name = "endYear")
        val endYear: Int?,
        @Json(name = "filmLength")
        val filmLength: Int?,
        @Json(name = "genres")
        val genres: List<Genre>?,
        @Json(name = "has3D")
        val has3D: Boolean?,
        @Json(name = "hasImax")
        val hasImax: Boolean?,
        @Json(name = "imdbId")
        val imdbId: String?,
        @Json(name = "isTicketsAvailable")
        val isTicketsAvailable: Boolean?,
        @Json(name = "kinopoiskId")
        var kinopoiskId: Int,
        @Json(name = "lastSync")
        val lastSync: String?,
        @Json(name = "logoUrl")
        val logoUrl: String?,
        @Json(name = "nameEn")
        val nameEn: String?,
        @Json(name = "nameOriginal")
        val nameOriginal: String?,
        @Json(name = "nameRu")
        val nameRu: String?,
        @Json(name = "posterUrl")
        val posterUrl: String?,
        @Json(name = "posterUrlPreview")
        val posterUrlPreview: String?,
        @Json(name = "productionStatus")
        val productionStatus: String?,
        @Json(name = "ratingAgeLimits")
        val ratingAgeLimits: String?,
        @Json(name = "ratingAwait")
        val ratingAwait: Double?,
        @Json(name = "ratingAwaitCount")
        val ratingAwaitCount: Int?,
        @Json(name = "ratingFilmCritics")
        val ratingFilmCritics: Double?,
        @Json(name = "ratingFilmCriticsVoteCount")
        val ratingFilmCriticsVoteCount: Int?,
        @Json(name = "ratingGoodReview")
        val ratingGoodReview: Double?,
        @Json(name = "ratingGoodReviewVoteCount")
        val ratingGoodReviewVoteCount: Int?,
        @Json(name = "ratingImdb")
        val ratingImdb: Double?,
        @Json(name = "ratingImdbVoteCount")
        val ratingImdbVoteCount: Int?,
        @Json(name = "ratingKinopoisk")
        val ratingKinopoisk: Double?,
        @Json(name = "ratingKinopoiskVoteCount")
        val ratingKinopoiskVoteCount: Int?,
        @Json(name = "ratingMpaa")
        val ratingMpaa: String?,
        @Json(name = "ratingRfCritics")
        val ratingRfCritics: Double?,
        @Json(name = "ratingRfCriticsVoteCount")
        val ratingRfCriticsVoteCount: Int?,
        @Json(name = "reviewsCount")
        val reviewsCount: Int?,
        @Json(name = "serial")
        val serial: Boolean?,
        @Json(name = "shortDescription")
        val shortDescription: String?,
        @Json(name = "shortFilm")
        val shortFilm: Boolean?,
        @Json(name = "slogan")
        val slogan: String?,
        @Json(name = "startYear")
        val startYear: Int?,
        @Json(name = "type")
        val type: String?,
        @Json(name = "webUrl")
        val webUrl: String?,
        @Json(name = "year")
        val year: Int?
    )

    {
        @JsonClass(generateAdapter = true)
        data class Country(
            @Json(name = "country")
            val country: String?
        )

        @JsonClass(generateAdapter = true)
        data class Genre(
            @Json(name = "genre")
            val genre: String?
        )
    }


