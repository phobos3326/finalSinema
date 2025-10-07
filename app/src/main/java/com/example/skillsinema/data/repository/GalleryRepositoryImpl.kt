package com.example.skillsinema.data.repository

import com.example.skillsinema.data.api.GalleryApi
import com.example.skillsinema.data.mapper.GalleryMapper
import com.example.skillsinema.domain.model.GalleryImage
import com.example.skillsinema.domain.repository.GalleryRepository
import javax.inject.Inject

class GalleryRepositoryImpl @Inject constructor(
    private val api: GalleryApi,
    private val mapper: GalleryMapper
) : GalleryRepository {

    override suspend fun getGallery(filmId: Int, type: String): List<GalleryImage> {
        val response = api.getGallery(filmId, type)
        return mapper.mapGalleryImageList(response.items)
    }
}