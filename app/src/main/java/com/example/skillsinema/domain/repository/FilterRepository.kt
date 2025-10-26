package com.example.skillsinema.domain.repository

import com.example.skillsinema.data.model.ModelFilter
import retrofit2.Response

interface FilterRepository {
    suspend fun getFilters(): Response<ModelFilter>
}