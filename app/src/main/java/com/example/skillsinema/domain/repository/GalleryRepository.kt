package com.example.skillsinema.domain.repository

import com.example.skillsinema.domain.model.GalleryImage

interface GalleryRepository {
    suspend fun getGallery(filmId: Int, type: String): List<GalleryImage>
}

