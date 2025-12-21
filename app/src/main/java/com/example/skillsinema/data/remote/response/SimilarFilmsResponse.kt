package com.example.skillsinema.data.remote.response

import com.example.skillsinema.domain.model.Film

data class SimilarFilmsResponse(
    val total: Int,
    val items: List<Film>
)
