package com.example.skillsinema.data

import com.example.skillsinema.entity.BestFilms
import com.example.skillsinema.entity.Film
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BestFilmDTO(
    @Json(name = "films")
    override val films: List<Film>,

    @Json(name = "pagesCount")
    override val pagesCount: Int
) : BestFilms