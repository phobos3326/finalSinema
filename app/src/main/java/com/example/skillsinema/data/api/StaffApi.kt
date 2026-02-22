package com.example.skillsinema.data.api

import com.example.skillsinema.data.dto.StaffDto
import retrofit2.http.*

interface StaffApi {

    @GET("v1/staff")
    suspend fun getStaff(
        @Query("filmId") filmId: Int
    ): List<StaffDto>
}
