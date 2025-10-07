package com.example.skillsinema.data.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GalleryImageDto(
    @Json(name = "imageUrl") val imageUrl: String,
    @Json(name = "previewUrl") val previewUrl: String
)