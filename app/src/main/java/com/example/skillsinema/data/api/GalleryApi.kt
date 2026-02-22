package com.example.skillsinema.data.api

import com.example.skillsinema.data.dto.GalleryResponseDto
import retrofit2.http.*

interface GalleryApi {

    @GET("v2.2/films/{id}/images")
    suspend fun getGallery(
        @Path("id") filmId: Int,
        @Query("type") type: String,
        @Query("page") page: Int = 1
    ): GalleryResponseDto
}
