package com.example.skillsinema.domain.gallery

import androidx.paging.PagingData
import com.example.skillsinema.domain.model.GalleryImage
import kotlinx.coroutines.flow.Flow

interface GetGalleryImagesUseCase {
    operator fun invoke(filmId: Int, imageType: String): Flow<PagingData<GalleryImage>>
}
