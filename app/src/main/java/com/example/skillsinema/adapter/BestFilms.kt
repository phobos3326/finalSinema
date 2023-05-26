package com.example.skillsinema.adapter


import com.example.skillsinema.entity.BestFilmDTO
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BestFilms(
    override val films: List<Film>, override val pagesCount: Int?
) : BestFilmDTO {
    @JsonClass(generateAdapter = true)
    data class Film(
        @Json(name = "countries")
        val countries: List<Country?>?,
        @Json(name = "filmId")
        val filmId: Int?,
        @Json(name = "filmLength")
        val filmLength: String?,
        @Json(name = "genres")
        val genres: List<Genre?>,
        @Json(name = "nameEn")
        val nameEn: String?,
        @Json(name = "nameRu")
        val nameRu: String,
        @Json(name = "posterUrl")
        val posterUrl: String?,
        @Json(name = "posterUrlPreview")
        val posterUrlPreview: String,
        @Json(name = "rating")
        val rating: String,
        @Json(name = "ratingChange")
        val ratingChange: Any?,
        @Json(name = "ratingVoteCount")
        val ratingVoteCount: Int?,
        @Json(name = "year")
        val year: String?
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
}