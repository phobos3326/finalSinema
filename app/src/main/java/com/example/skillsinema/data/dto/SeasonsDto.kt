package com.example.skillsinema.data.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SeasonsDto(
    @Json(name = "total") val total: Int,
    @Json(name = "items") val items: List<SeasonDto>
)

@JsonClass(generateAdapter = true)
data class SeasonDto(
    @Json(name = "number") val number: Int,
    @Json(name = "episodes") val episodes: List<EpisodeDto>
)

@JsonClass(generateAdapter = true)
data class EpisodeDto(
    @Json(name = "seasonNumber") val seasonNumber: Int,
    @Json(name = "episodeNumber") val episodeNumber: Int,
    @Json(name = "nameRu") val nameRu: String?,
    @Json(name = "nameEn") val nameEn: String?,
    @Json(name = "synopsis") val synopsis: String?,
    @Json(name = "releaseDate") val releaseDate: String?
)