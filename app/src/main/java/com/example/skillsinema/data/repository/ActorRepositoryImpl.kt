package com.example.skillsinema.data.repository

import com.example.skillsinema.data.api.ActorApi
import com.example.skillsinema.data.mapper.ActorMapper
import com.example.skillsinema.domain.model.Actor
import com.example.skillsinema.domain.model.FilmParticipation
import com.example.skillsinema.domain.repository.ActorRepository
import javax.inject.Inject

class ActorRepositoryImpl @Inject constructor(
    private val api: ActorApi,
    private val mapper: ActorMapper
) : ActorRepository {

    override suspend fun getActorInfo(actorId: Int): Actor {
        val response = api.getActorInfo(actorId)
        return mapper.mapActor(response)
    }

    override suspend fun getActorFilms(actorId: Int): List<FilmParticipation> {
        val response = api.getActorFilms(actorId)
        return response.map { dto ->
            FilmParticipation(
                filmId = dto.filmId,
                nameRu = dto.nameRu,
                nameEn = dto.nameEn,
                rating = dto.rating,
                general = dto.general,
                description = dto.description,
                professionText = dto.professionText,
                professionKey = dto.professionKey
            )
        }
    }
}