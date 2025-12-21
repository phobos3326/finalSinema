package com.example.skillsinema.data

import com.example.skillsinema.entity.Model
import com.example.skillsinema.entity.Film
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DataDTO(
    @Json(name = "items")
    override val items: List<Film>
) : Model
