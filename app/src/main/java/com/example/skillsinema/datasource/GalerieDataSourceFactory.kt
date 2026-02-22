package com.example.skillsinema.datasource

import com.example.skillsinema.domain.GalerieUseCase
import com.example.skillsinema.repository.GalerieRepository
import javax.inject.Inject

class GalerieDataSourceFactory @Inject constructor(
    private val repository: GalerieRepository,
    private val useCase: GalerieUseCase
) {
    fun create(filmId: Int, imageType: String): GalerieDataSource {
        return GalerieDataSource(repository, useCase)
    }
}
