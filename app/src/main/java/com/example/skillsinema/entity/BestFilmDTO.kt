package com.example.skillsinema.entity

import com.example.skillsinema.adapter.BestFilms
import com.squareup.moshi.Json

interface BestFilmDTO {

    val films: List<BestFilms.Film>

    val pagesCount: Int?
}