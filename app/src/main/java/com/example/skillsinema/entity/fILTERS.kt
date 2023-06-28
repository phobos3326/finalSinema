package com.example.skillsinema.entity


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class fILTERS(
    @Json(name = "countries")
    val countries: List<Any>,
    @Json(name = "genres")
    val genres: List<Any>
)