package com.example.skillsinema.domain.model

data class Movie(
    val id: Long,
    val title: String,
    val overview: String,
    val posterUrl: String?
)