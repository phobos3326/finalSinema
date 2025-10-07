package com.example.skillsinema.data.api

import com.example.skillsinema.data.dto.ActorDto
import com.example.skillsinema.data.dto.ActorFilmsDto
import retrofit2.http.*

interface ActorApi {

    @Headers("X-API-KEY: ${KinopoiskApi.API_KEY}")
    @GET("staff/{id}")
    suspend fun getActorInfo(
        @Path("id") actorId: Int
    ): ActorDto

    @Headers("X-API-KEY: ${KinopoiskApi.API_KEY}")
    @GET("staff/{id}/films")
    suspend fun getActorFilms(
        @Path("id") actorId: Int
    ): List<ActorFilmsDto>
}
