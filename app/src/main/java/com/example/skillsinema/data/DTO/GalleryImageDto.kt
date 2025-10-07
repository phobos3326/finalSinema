package com.example.skillsinema.data.DTO

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GalleryImageDto(
    @Json(name = "imageUrl") val imageUrl: String,
    @Json(name = "previewUrl") val previewUrl: String
)