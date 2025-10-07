package com.example.skillsinema.data.DTO

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PremiereResponseDto(
    @Json(name = "total") val total: Int,
    @Json(name = "items") val items: List<FilmDto>
)