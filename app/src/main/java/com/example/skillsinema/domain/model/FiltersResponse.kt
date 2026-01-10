package com.example.skillsinema.domain.model


data class FiltersResponse(
    val genres: List<Genre>,
    val countries: List<Country>
)

