package com.example.skillsinema.data.repository


import com.example.skillsinema.data.api.KinopoiskApi
import com.example.skillsinema.data.mapper.FilterMapper
import com.example.skillsinema.data.model.ModelFilter
import com.example.skillsinema.domain.repository.FilterRepository

import retrofit2.Response
import javax.inject.Inject

class FilterRepositoryImpl @Inject constructor(
    private val api: KinopoiskApi,
    private val mapper: FilterMapper
) : FilterRepository {

    override suspend fun getFilters(): Response<ModelFilter> {
        val response = api.getFilters()
        return if (response.isSuccessful && response.body() != null) {
            val mappedFilters = mapper.mapFiltersDto(response.body()!!)
            Response.success(mappedFilters)
        } else {
            Response.error(response.code(), response.errorBody()!!)
        }
    }
}