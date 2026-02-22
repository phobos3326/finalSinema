package com.example.skillsinema.datasource

import com.example.skillsinema.DataRepository
import javax.inject.Inject

class GalerieDataSourceFactory @Inject constructor(
    private val dataRepository: DataRepository
) {
    fun create(filmId: Int, imageType: String): GalerieDataSource {
        return GalerieDataSource(dataRepository, filmId, imageType)
    }
}
