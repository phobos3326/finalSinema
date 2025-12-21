package com.example.skillsinema.entity


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

interface Model {
    val items: List<Film>
}