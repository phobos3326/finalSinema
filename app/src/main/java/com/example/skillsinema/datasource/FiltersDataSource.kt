package com.example.skillsinema.datasource

import android.util.Log
import com.example.skillsinema.domain.FiltersUseCase
import com.example.skillsinema.entity.ModelFilter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
/*

@Module
@InstallIn(SingletonComponent::class)
class FiltersDataSource @Inject constructor(

) {
    @Inject
    lateinit var  useCase: FiltersUseCase

    @Provides
    suspend fun loadGenre(): List<ModelFilter.Genre> {
        var listFilters = listOf<ModelFilter.Genre>()
        kotlin.runCatching {
            useCase.getFilters().genres
        }.fold(
            onSuccess = {

                listFilters = it
               Log.d("MainViewModel2", it.toString())
            },
            onFailure = { Log.d("MainViewModelFilters", it.message ?: "not load") }
        )
        return listFilters

    }

    @Provides
    suspend fun loadCountry(): List<ModelFilter.Country> {
        var listFilters = listOf<ModelFilter.Country>()
        kotlin.runCatching {
            useCase.getFilters()
        }.fold(
            onSuccess = {

                listFilters = it.countries
                Log.d("MainViewModel3", it.toString())
            },
            onFailure = { Log.d("MainViewModelFilters", it.message ?: "not load") }
        )
        return listFilters

    }
}*/
