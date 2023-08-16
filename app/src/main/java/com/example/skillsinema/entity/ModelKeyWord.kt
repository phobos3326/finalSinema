package com.example.skillsinema.entity


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ModelKeyWord(
    @Json(name = "films")
    val films: List<Film>?,
    @Json(name = "keyword")
    val keyword: String?,
    @Json(name = "pagesCount")
    val pagesCount: Int?,
    @Json(name = "searchFilmsCountResult")
    val searchFilmsCountResult: Int?
) {

}