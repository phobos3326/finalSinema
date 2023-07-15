package com.example.skillsinema.data

import com.example.skillsinema.entity.BestFilms
import com.example.skillsinema.entity.Film

data class BestFilmDTO(
    override val films: List<Film>, override val pagesCount: Int
    ) : BestFilms