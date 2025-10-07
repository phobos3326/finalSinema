package com.example.skillsinema.data.mapper

import com.example.skillsinema.data.dto.GalleryImageDto
import com.example.skillsinema.domain.model.GalleryImage
import javax.inject.Inject

class GalleryMapper @Inject constructor() {

    fun mapGalleryImage(dto: GalleryImageDto): GalleryImage {
        return GalleryImage(
            imageUrl = dto.imageUrl,
            previewUrl = dto.previewUrl
        )
    }

    fun mapGalleryImageList(dtos: List<GalleryImageDto>): List<GalleryImage> {
        return dtos.map { mapGalleryImage(it) }
    }
}