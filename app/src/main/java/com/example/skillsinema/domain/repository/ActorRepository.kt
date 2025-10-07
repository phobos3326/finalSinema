package com.example.skillsinema.domain.repository

import com.example.skillsinema.domain.model.Actor
import com.example.skillsinema.domain.model.FilmParticipation

interface ActorRepository {
    suspend fun getActorInfo(actorId: Int): Actor
    suspend fun getActorFilms(actorId: Int): List<FilmParticipation>
}