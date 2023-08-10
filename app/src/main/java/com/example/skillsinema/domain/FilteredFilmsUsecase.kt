package com.example.skillsinema.domain

import com.example.skillsinema.DataRepository
import com.example.skillsinema.entity.Film
import com.example.skillsinema.repository.Repository
import javax.inject.Inject

class FilteredFilmsUseCase @Inject constructor(
    private val repository: Repository,
    private val dataRepository: DataRepository
) {

   suspend fun getFilteredFilms(page:Int): List<Film> {

        return repository.getFilteredFilm(page,dataRepository.countryID, dataRepository.genreID)
    }

}