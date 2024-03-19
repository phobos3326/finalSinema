package com.example.skillsinema.entity


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
interface Model {
    val items: List<Film>

/*    data class Item(
        @Json(name = "countries")
        val countries: List<Country>,
        @Json(name = "duration")
        val duration: Int?,
        @Json(name = "genres")
        val genres: List<Genre>,
        @Json(name = "kinopoiskId")
        val kinopoiskId: Int,
        @Json(name = "nameEn")
        val nameEn: String,
        @Json(name = "nameRu")
        val nameRu: String,
        @Json(name = "posterUrl")
        val posterUrl: String,
        @Json(name = "posterUrlPreview")
        val posterUrlPreview: String,
        @Json(name = "premiereRu")
        val premiereRu: String,
        @Json(name = "year")
        val year: Int)

    {

        data class Country(
            @Json(name = "country")
            val country: String
        )

        data class Genre(
            @Json(name = "genre")
            val genre: String
        )
    }*/
}
