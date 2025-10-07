package com.example.skillsinema.data.DTO

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FilmParticipationDto(
    @Json(name = "filmId") val filmId: Int,
    @Json(name = "nameRu") val nameRu: String?,
    @Json(name = "nameEn") val nameEn: String?,
    @Json(name = "rating") val rating: String?,
    @Json(name = "general") val general: Boolean?,
    @Json(name = "description") val description: String?,
    @Json(name = "professionText") val professionText: String?,
    @Json(name = "professionKey") val professionKey: String?
)