package com.example.skillsinema.data.mapper

import com.example.skillsinema.data.dto.CountryDto
import com.example.skillsinema.data.dto.FiltersDto
import com.example.skillsinema.data.dto.GenreDto
import com.example.skillsinema.domain.model.Country
import com.example.skillsinema.domain.model.FiltersResponse
import com.example.skillsinema.domain.model.Genre
import javax.inject.Inject

class FiltersMapper @Inject constructor() {

    fun map(dto: FiltersDto): FiltersResponse {
        return FiltersResponse(
            genres = dto.genres.mapNotNull(::mapGenre),
            countries = dto.countries.mapNotNull(::mapCountry)
        )
    }

    private fun mapGenre(dto: GenreDto): Genre? {
        val id = dto.id ?: return null
        val name = dto.genre ?: return null
        return Genre(id = id, genre = name)
    }

    private fun mapCountry(dto: CountryDto): Country? {
        val id = dto.id ?: return null
        val name = dto.country ?: return null
        return Country(id = id, country = name)
    }
}
