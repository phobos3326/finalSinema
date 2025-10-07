package com.example.skillsinema.data.api

import com.example.skillsinema.data.dto.GalleryResponseDto
import retrofit2.http.*

interface GalleryApi {

    @Headers("X-API-KEY: ${KinopoiskApi.API_KEY}")
    @GET("films/{id}/images")
    suspend fun getGallery(
        @Path("id") filmId: Int,
        @Query("type") type: String,
        @Query("page") page: Int = 1
    ): GalleryResponseDto
}