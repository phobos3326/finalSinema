package com.example.skillsinema.data.repository


import com.example.skillsinema.data.api.KinopoiskApi
import com.example.skillsinema.domain.repository.FilterRepository
import com.example.skillsinema.entity.ModelFilter
import retrofit2.Response
import javax.inject.Inject

class FilterRepositoryImpl @Inject constructor(
    private val api: KinopoiskApi
) : FilterRepository {

    override suspend fun getFilters(): Response<ModelFilter> {
        // Временная реализация - нужно адаптировать под новые DTOs
        return api.getFilters() as Response<ModelFilter>
    }
}