package com.example.skillsinema.domain.model


data class FiltersResponse(
    val genres: List<Genre>,
    val countries: List<Country>
)

data class Genre(

    val genre: String,
    val id: Int,
)

data class Country(
    val country: String,
    val id: Int

)