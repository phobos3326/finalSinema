package com.example.skillsinema.domain

import com.example.skillsinema.entity.ModelFilteredFilms
import com.example.skillsinema.entity.ModelFilteredFilms1
import com.example.skillsinema.repository.Repository
import javax.inject.Inject

class FilteredFilmsUseCase @Inject constructor(private val repository: Repository) {

   suspend fun getFilteredFilms(): List<ModelFilteredFilms1> {
        return repository.getFilteredFilm(1,11)
    }

}