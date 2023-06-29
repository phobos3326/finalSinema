package com.example.skillsinema.datasource

import android.util.Log
import com.example.skillsinema.domain.FiltersUseCase
import com.example.skillsinema.entity.ModelFilter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject

@Module
@InstallIn(SingletonComponent::class)
class FiltersDataSource @Inject constructor(

) {
    @Inject
    lateinit var  useCase: FiltersUseCase

    @Provides
    suspend fun loadFilters(): List<ModelFilter> {
        var listFilters = listOf<ModelFilter>()
        kotlin.runCatching {
            useCase.getFilters()
        }.fold(
            onSuccess = {

                listFilters = listOf(it)
                Log.d("MainViewModel2", (it ?: " load").toString())
            },
            onFailure = { Log.d("MainViewModelFilters", it.message ?: "not load") }
        )
        return listFilters

    }
}