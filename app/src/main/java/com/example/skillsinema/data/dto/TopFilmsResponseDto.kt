package com.example.skillsinema.data.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TopFilmsResponseDto(
    @Json(name = "pagesCount") val pagesCount: Int,
    @Json(name = "films") val films: List<FilmDto>
)
