package com.example.skillsinema.entity


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ModelFilter(
    @Json(name = "countries")
    val countries: List<com.example.skillsinema.domain.model.Country>,
    @Json(name = "genres")
    val genres: List<com.example.skillsinema.domain.model.Genre>
) {
    @JsonClass(generateAdapter = true)
    data class Country(
        @Json(name = "country")
        val country: String,
        @Json(name = "id")
        val id: Int
    )

    @JsonClass(generateAdapter = true)
    data class Genre(
        @Json(name = "genre")
        val genre: String,
        @Json(name = "id")
        val id: Int
    )
}