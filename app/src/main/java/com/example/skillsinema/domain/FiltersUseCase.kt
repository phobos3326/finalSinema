package com.example.skillsinema.domain

import com.example.skillsinema.DataRepository
import com.example.skillsinema.entity.ModelFilter
import com.example.skillsinema.repository.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import retrofit2.Response

import javax.inject.Inject

class FiltersUseCase @Inject constructor(
    private val repository: Repository,
    private val dataRepository: DataRepository
) {
    suspend fun getFilters(): Response<ModelFilter> {
        return repository.getFilters()
    }

}