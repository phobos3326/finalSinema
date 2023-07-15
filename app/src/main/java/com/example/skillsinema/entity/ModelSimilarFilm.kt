package com.example.skillsinema.entity


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ModelSimilarFilm(
    @Json(name = "items")
    val items: List<Film>,
    @Json(name = "total")
    val total: Int
) {
    @JsonClass(generateAdapter = true)
    data class Item(
        @Json(name = "filmId")
        val filmId: Int,
        @Json(name = "nameEn")
        val nameEn: String,
        @Json(name = "nameOriginal")
        val nameOriginal: String,
        @Json(name = "nameRu")
        val nameRu: String,
        @Json(name = "posterUrl")
        val posterUrl: String,
        @Json(name = "posterUrlPreview")
        val posterUrlPreview: String,
        @Json(name = "relationType")
        val relationType: String
    )
}