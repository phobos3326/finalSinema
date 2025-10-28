package com.example.skillsinema.data.mapper

import com.example.skillsinema.data.dto.FiltersDto
import com.example.skillsinema.data.dto.GenreDto
import com.example.skillsinema.data.dto.CountryDto
import com.example.skillsinema.domain.model.Genre
import com.example.skillsinema.domain.model.Country
import com.example.skillsinema.data.model.ModelFilter
import javax.inject.Inject

class FilterMapper @Inject constructor() {

    fun mapFiltersDto(dto: FiltersDto): ModelFilter {
        return ModelFilter(
            genres = dto.genres.mapNotNull { mapGenreDto(it) },     // ← mapNotNull вместо map
            countries = dto.countries.mapNotNull { mapCountryDto(it) } // ← mapNotNull вместо map
        )
    }

    private fun mapGenreDto(dto: GenreDto): Genre? {
        return if (dto.id != null) {  // ← Проверка на null
            Genre(
                id = dto.id,
                genre = dto.genre
            )
        } else {
            null  // ← Пропускаем элементы без id
        }
    }

    private fun mapCountryDto(dto: CountryDto): Country? {
        return if (dto.id != null) {  // ← Проверка на null
            Country(
                id = dto.id,
                country = dto.country
            )
        } else {
            null  // ← Пропускаем элементы без id
        }
    }
}