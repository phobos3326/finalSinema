package com.example.skillsinema.domain.usecase.gallery

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.skillsinema.data.local.paging.GalleryPagingSource
import com.example.skillsinema.domain.model.GalleryImage
import com.example.skillsinema.domain.repository.GalleryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetGalleryImagesUseCase @Inject constructor(
    private val galleryRepository: GalleryRepository
) {
    operator fun invoke(filmId: Int, type: String): Flow<PagingData<GalleryImage>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false,
                prefetchDistance = 5,
                initialLoadSize = 20
            ),
            pagingSourceFactory = {
                GalleryPagingSource(galleryRepository, filmId, type)
            }
        ).flow
    }
}
