package com.example.skillsinema.domain.repository

import com.example.skillsinema.domain.model.FiltersResponse

interface FilterRepository {
    suspend fun getFilters(): FiltersResponse
}