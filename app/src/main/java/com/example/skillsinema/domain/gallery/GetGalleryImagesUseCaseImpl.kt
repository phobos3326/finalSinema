package com.example.skillsinema.domain.gallery

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.skillsinema.data.local.paging.GalleryPagingSource
import com.example.skillsinema.domain.model.GalleryImage
import com.example.skillsinema.domain.repository.GalleryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetGalleryImagesUseCaseImpl @Inject constructor(
    private val repository: GalleryRepository
) : GetGalleryImagesUseCase {

    override fun invoke(filmId: Int, imageType: String): Flow<PagingData<GalleryImage>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false,
                prefetchDistance = 5,
                initialLoadSize = 20
            ),
            pagingSourceFactory = {
                GalleryPagingSource(repository, filmId, imageType)
            }
        ).flow
    }
}
