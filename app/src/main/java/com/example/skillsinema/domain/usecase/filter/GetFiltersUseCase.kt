package com.example.skillsinema.domain.usecase.filter

import com.example.skillsinema.data.model.ModelFilter
import com.example.skillsinema.domain.repository.FilterRepository

import javax.inject.Inject

class GetFiltersUseCase @Inject constructor(
    private val repository: FilterRepository
) {
    suspend operator fun invoke(): ModelFilter {
        val response = repository.getFilters()
        return if (response.isSuccessful) {
            response.body()!!
        } else {
            throw Exception("Failed to load filters: ${response.message()}")
        }
    }
}