package com.example.skillsinema.data.remote.response

import com.example.skillsinema.domain.model.Film

data class FilmsResponse(
    val total: Int,
    val totalPages: Int,
    val items: List<Film>
)
