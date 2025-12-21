package com.example.skillsinema.data.remote.response

import com.example.skillsinema.domain.model.Film

data class SearchResponse(
    val keyword: String,
    val pagesCount: Int,
    val searchFilmsCountResult: Int,
    val films: List<Film>
)
