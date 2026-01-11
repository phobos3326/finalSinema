package com.example.skillsinema.data.mapper

import com.example.skillsinema.data.dto.PremiereResponseDto
import com.example.skillsinema.data.dto.FilteredFilmsResponseDto
import com.example.skillsinema.data.remote.response.TopFilmsResponseDto
import com.example.skillsinema.domain.model.Film
import javax.inject.Inject

class PagingMapper @Inject constructor(
    private val filmMapper: FilmMapper
) {

    fun mapPremiereResponse(dto: PremiereResponseDto): List<Film> {
        return filmMapper.mapFilmList(dto.items)
    }

    fun mapTopFilmsResponse(dto: TopFilmsResponseDto): List<Film> {
        return filmMapper.mapFilmList(dto.films)
    }

    fun mapFilteredFilmsResponse(dto: FilteredFilmsResponseDto): List<Film> {
        return filmMapper.mapFilmList(dto.items)
    }
}