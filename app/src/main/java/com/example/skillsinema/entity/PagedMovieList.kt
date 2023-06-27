package com.example.skillsinema.entity

import com.example.skillsinema.adapter.Film

class PagedMovieList(
    val page: Int,
    val films: List<Film>
)