package com.example.skillsinema.data.remote.response

import com.example.skillsinema.domain.model.Country
import com.example.skillsinema.domain.model.Genre

data class FiltersResponse(
    val genres: List<Genre>,
    val countries: List<Country>
)
