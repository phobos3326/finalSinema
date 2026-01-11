package com.example.skillsinema.domain.usecase.filter

import com.example.skillsinema.domain.model.FiltersResponse
import com.example.skillsinema.domain.repository.FilterRepository
import javax.inject.Inject

class GetFiltersUseCase @Inject constructor(
    private val repository: FilterRepository
) {
    suspend operator fun invoke(): FiltersResponse {
        return repository.getFilters()
    }
}
