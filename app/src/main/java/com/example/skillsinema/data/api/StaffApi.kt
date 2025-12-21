package com.example.skillsinema.data.api

import com.example.skillsinema.data.dto.StaffDto
import retrofit2.http.*

interface StaffApi {

   // @Headers("X-API-KEY: ${KinopoiskApi.API_KEY}")
    @GET("films/{id}/staff")
    suspend fun getStaff(
        @Path("id") filmId: Int
    ): List<StaffDto>
}