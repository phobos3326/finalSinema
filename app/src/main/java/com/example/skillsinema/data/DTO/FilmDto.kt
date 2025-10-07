package com.example.skillsinema.data.DTO

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FilmDto(
    @Json(name = "kinopoiskId") val kinopoiskId: Int,
    @Json(name = "filmId") val filmId: Int?,
    @Json(name = "nameRu") val nameRu: String?,
    @Json(name = "nameEn") val nameEn: String?,
    @Json(name = "year") val year: String?,
    @Json(name = "posterUrl") val posterUrl: String?,
    @Json(name = "posterUrlPreview") val posterUrlPreview: String?,
    @Json(name = "countries") val countries: List<CountryDto> = emptyList(),
    @Json(name = "genres") val genres: List<GenreDto> = emptyList(),
    @Json(name = "duration") val duration: Int?,
    @Json(name = "premiereRu") val premiereRu: String?,
    @Json(name = "rating") val rating: Double?,
    @Json(name = "ratingVoteCount") val ratingVoteCount: Int?
)