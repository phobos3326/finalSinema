package com.example.skillsinema.data.mapper


import com.example.skillsinema.data.remote.response.SearchResponseDto
import com.example.skillsinema.domain.model.Film
import javax.inject.Inject

class SearchMapper @Inject constructor(
    private val filmMapper: FilmMapper
) {

    fun mapSearchResponse(dto: SearchResponseDto): List<Film> {
        return filmMapper.mapFilmList(dto.films)
    }
}