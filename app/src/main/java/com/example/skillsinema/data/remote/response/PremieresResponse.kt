package com.example.skillsinema.data.remote.response

import com.example.skillsinema.domain.model.Film

data class PremieresResponse(
    val total: Int,
    val items: List<Film>
)
