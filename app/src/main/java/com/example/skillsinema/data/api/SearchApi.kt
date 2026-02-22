package com.example.skillsinema.data.api

import com.example.skillsinema.data.remote.response.SearchResponseDto
import retrofit2.http.*

interface SearchApi {

    @GET("v2.1/films/search-by-keyword")
    suspend fun searchFilms(
        @Query("keyword") keyword: String,
        @Query("page") page: Int = 1
    ): SearchResponseDto
}
