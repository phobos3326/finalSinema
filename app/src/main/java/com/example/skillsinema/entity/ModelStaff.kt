package com.example.skillsinema.entity


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
interface ModelStaff {
    @Json(name = "staffItem")
    val staffItem: List<ModelStaffItem>
}

@JsonClass(generateAdapter = true)
data class ModelStaffItem(
    @Json(name = "description")
    val description: String,
    @Json(name = "nameEn")
    val nameEn: String,
    @Json(name = "nameRu")
    val nameRu: String,
    @Json(name = "posterUrl")
    val posterUrl: String,
    @Json(name = "professionKey")
    val professionKey: String,
    @Json(name = "professionText")
    val professionText: String,
    @Json(name = "staffId")
    val staffId: Int
)