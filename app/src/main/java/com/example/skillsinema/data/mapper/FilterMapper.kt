package com.example.skillsinema.data.mapper

import com.example.skillsinema.data.dto.FiltersDto
import com.example.skillsinema.data.dto.GenreDto
import com.example.skillsinema.data.dto.CountryDto
import com.example.skillsinema.domain.model.Genre
import com.example.skillsinema.domain.model.Country
import com.example.skillsinema.entity.ModelFilter
import javax.inject.Inject

class FilterMapper @Inject constructor() {

    fun mapFiltersDto(dto: FiltersDto): ModelFilter {
        return ModelFilter(
            genres = dto.genres.map { mapGenreDto(it) },
            countries = dto.countries.map { mapCountryDto(it) }
        )
    }

    private fun mapGenreDto(dto: GenreDto): Genre {
        return Genre(
            id = dto.id,
            genre = dto.genre
        )
    }

    private fun mapCountryDto(dto: CountryDto): Country {
        return Country(
            id = dto.id,
            country = dto.country
        )
    }
}