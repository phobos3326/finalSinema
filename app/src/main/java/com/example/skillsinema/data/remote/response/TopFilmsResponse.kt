package com.example.skillsinema.data.remote.response

import com.example.skillsinema.domain.model.Film

data class TopFilmsResponse(
    val pagesCount: Int,
    val films: List<Film>
)
