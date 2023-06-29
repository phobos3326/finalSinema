package com.example.skillsinema.domain

import com.example.skillsinema.entity.ModelFilter
import com.example.skillsinema.repository.Repository

import javax.inject.Inject

class FiltersUseCase @Inject constructor(private val repository: Repository) {


    suspend fun getFilters(): List<ModelFilter.Genre> {
        return repository.getFilters()
    }


}