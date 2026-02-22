package com.example.skillsinema.data.api

import com.example.skillsinema.data.dto.ActorDto
import com.example.skillsinema.data.dto.ActorFilmsDto
import retrofit2.http.*

interface ActorApi {

    @GET("v1/staff/{id}")
    suspend fun getActorInfo(
        @Path("id") actorId: Int
    ): ActorDto

    @GET("v1/staff/{id}/films")
    suspend fun getActorFilms(
        @Path("id") actorId: Int
    ): List<ActorFilmsDto>
}
