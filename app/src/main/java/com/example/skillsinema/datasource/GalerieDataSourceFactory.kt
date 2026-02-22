package com.example.skillsinema.datasource

import com.example.skillsinema.data.local.paging.GalleryPagingSource
import com.example.skillsinema.domain.repository.GalleryRepository
import javax.inject.Inject

class GalerieDataSourceFactory @Inject constructor(
    private val repository: GalleryRepository
) {
    fun create(filmId: Int, imageType: String): GalleryPagingSource {
        return GalleryPagingSource(repository, filmId, imageType)
    }
}
