package com.example.skillsinema.data.DTO

@JsonClass(generateAdapter = true)
data class SearchResponseDto(
    @Json(name = "total") val total: Int,
    @Json(name = "totalPages") val totalPages: Int,
    @Json(name = "items") val items: List<FilmDto>
)