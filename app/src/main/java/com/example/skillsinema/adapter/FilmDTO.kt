package com.example.skillsinema.adapter

import com.example.skillsinema.entity.ModelFilmDetails
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class FilmDTO(override val film: ModelFilmDetails.Film) :ModelFilmDetails