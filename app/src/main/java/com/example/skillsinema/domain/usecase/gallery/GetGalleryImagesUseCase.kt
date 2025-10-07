package com.example.skillsinema.domain.usecase.gallery

import com.example.skillsinema.domain.model.GalleryImage
import com.example.skillsinema.domain.repository.GalleryRepository
import javax.inject.Inject

class GetGalleryImagesUseCase @Inject constructor(
    private val galleryRepository: GalleryRepository
) {
    suspend operator fun invoke(filmId: Int, type: String): List<GalleryImage> {
        return galleryRepository.getGallery(filmId, type)
    }
}