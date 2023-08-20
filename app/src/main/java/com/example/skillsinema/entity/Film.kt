package com.example.skillsinema.entity

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


data class Film(
    @Json(name = "countries")
    val countries: List<Country?>?,
    @Json(name = "description")
    val description: String?,
    @Json(name = "filmId")
    val filmId: Int?,
    @Json(name = "filmLength")
    val filmLength: String?,
    @Json(name = "genres")
    val genres: List<Genre>?,
    @Json(name = "nameEn")
    val nameEn: String?,
    @Json(name = "nameRu")
    val nameRu: String?,
    @Json(name = "posterUrl")
    val posterUrl: String?,
    @Json(name = "posterUrlPreview")
    var posterUrlPreview: String?,
    @Json(name = "rating")
    val rating: String?,
    @Json(name = "ratingVoteCount")
    val ratingVoteCount: Int?,
    @Json(name = "type")
    val type: String?,
    @Json(name = "year")
    val year: String?,
    @Json(name = "kinopoiskId")
    val kinopoiskId: Int?,
    @Json(name = "ratingImdb")
    val ratingImdb: String?,
    @Json(name = "isViewed")
    var isViewed: Boolean?

) {
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
