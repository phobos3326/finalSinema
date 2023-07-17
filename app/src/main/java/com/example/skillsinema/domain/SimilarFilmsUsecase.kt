package com.example.skillsinema.domain

import com.example.skillsinema.DataRepository
import com.example.skillsinema.entity.Film
import com.example.skillsinema.repository.RepositorySimilarFilm
import javax.inject.Inject

class SimilarFilmsUsecase @Inject constructor(
    val dataRepository: DataRepository,
    val repositorySimilarFilm: RepositorySimilarFilm
) {
    suspend fun getSimilarFilms(): List<Film> {

      return  repositorySimilarFilm.getSimilarFilm(dataRepository.id)
    }
}