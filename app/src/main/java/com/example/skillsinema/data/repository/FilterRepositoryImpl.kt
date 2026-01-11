package com.example.skillsinema.data.repository

import com.example.skillsinema.data.api.KinopoiskApi
import com.example.skillsinema.data.mapper.FiltersMapper
import com.example.skillsinema.domain.model.FiltersResponse
import com.example.skillsinema.domain.repository.FilterRepository
import javax.inject.Inject

class FilterRepositoryImpl @Inject constructor(
    private val api: KinopoiskApi,
    private val filtersMapper: FiltersMapper
) : FilterRepository {

    override suspend fun getFilters(): FiltersResponse {
        val response = api.getFilters()
        if (!response.isSuccessful) throw Exception("Failed: ${response.code()} ${response.message()}")
        val dto = response.body() ?: throw Exception("Empty filters response")
        return filtersMapper.map(dto) // -> FiltersResponse
    }
}
