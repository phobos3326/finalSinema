package com.example.skillsinema.entity


import com.example.skillsinema.adapter.Film
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ModelFilteredFilms1(
    @Json(name = "items")
    val items: List<Film>,
    @Json(name = "total")
    val total: Int,
    @Json(name = "totalPages")
    val totalPages: Int
)