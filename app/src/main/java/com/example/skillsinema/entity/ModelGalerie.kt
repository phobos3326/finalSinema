package com.example.skillsinema.entity


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ModelGalerie (
    @Json(name = "items")
    val items: List<Item>,
    @Json(name = "total")
    val total: Int,
    @Json(name = "totalPages")
    val totalPages: Int
){
    @JsonClass(generateAdapter = true)
    data class Item(
        @Json(name = "imageUrl")
        val imageUrl: String,
        @Json(name = "previewUrl")
        val previewUrl: String
    )
}

