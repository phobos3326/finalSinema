package com.example.skillsinema.data.remote.response

import com.example.skillsinema.data.dto.FilmDto
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TopFilmsResponseDto(
    @Json(name = "pagesCount") val pagesCount: Int,
    @Json(name = "films") val films: List<FilmDto> = emptyList()
)
