package com.example.skillsinema.data.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ActorDto(
    @Json(name = "personId") val personId: Int,
    @Json(name = "webUrl") val webUrl: String?,
    @Json(name = "nameRu") val nameRu: String?,
    @Json(name = "nameEn") val nameEn: String?,
    @Json(name = "sex") val sex: String?,
    @Json(name = "posterUrl") val posterUrl: String?,
    @Json(name = "growth") val growth: String?,
    @Json(name = "birthday") val birthday: String?,
    @Json(name = "death") val death: String?,
    @Json(name = "age") val age: Int?,
    @Json(name = "birthplace") val birthplace: String?,
    @Json(name = "deathplace") val deathplace: String?,
    @Json(name = "hasAwards") val hasAwards: Int?,
    @Json(name = "profession") val profession: String?,
    @Json(name = "facts") val facts: List<String>,
    @Json(name = "spouses") val spouses: List<SpouseDto>,
    @Json(name = "films") val films: List<FilmParticipationDto>
)
