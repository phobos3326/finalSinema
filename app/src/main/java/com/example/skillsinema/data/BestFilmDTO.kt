package com.example.skillsinema.data

import com.example.skillsinema.adapter.BestFilms
import com.example.skillsinema.adapter.Film
import com.squareup.moshi.Json

data class BestFilmDTO(
    override val films: List<Film>, override val pagesCount: Int
    ) :BestFilms