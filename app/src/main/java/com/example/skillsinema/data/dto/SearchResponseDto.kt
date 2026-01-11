package com.example.skillsinema.data.remote.response

import com.example.skillsinema.data.dto.FilmDto
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SearchResponseDto(
    @Json(name = "keyword") val keyword: String,
    @Json(name = "pagesCount") val pagesCount: Int,
    @Json(name = "searchFilmsCountResult") val searchFilmsCountResult: Int,
    @Json(name = "films") val films: List<FilmDto> = emptyList()
)
