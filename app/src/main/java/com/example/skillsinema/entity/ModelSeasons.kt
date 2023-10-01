package com.example.skillsinema.entity


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ModelSeasons(
    @Json(name = "items")
    val items: List<Item>,
    @Json(name = "total")
    val total: Int
) {
    @JsonClass(generateAdapter = true)
    data class Item(
        @Json(name = "episodes")
        val episodes: List<Episode>,
        @Json(name = "number")
        val number: Int
    ) {
        @JsonClass(generateAdapter = true)
        data class Episode(
            @Json(name = "episodeNumber")
            val episodeNumber: Int,
            @Json(name = "nameEn")
            val nameEn: String,
            @Json(name = "nameRu")
            val nameRu: Any?,
            @Json(name = "releaseDate")
            val releaseDate: String,
            @Json(name = "seasonNumber")
            val seasonNumber: Int,
            @Json(name = "synopsis")
            val synopsis: Any?
        )
    }
}