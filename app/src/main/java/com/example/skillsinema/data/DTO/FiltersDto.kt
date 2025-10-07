package com.example.skillsinema.data.DTO

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FiltersDto(
    @Json(name = "genres") val genres: List<GenreDto>,
    @Json(name = "countries") val countries: List<CountryDto>
)