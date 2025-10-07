package com.example.skillsinema.data.mapper

import com.example.skillsinema.data.dto.ActorDto
import com.example.skillsinema.data.dto.FilmParticipationDto
import com.example.skillsinema.domain.model.Actor
import com.example.skillsinema.domain.model.FilmParticipation
import javax.inject.Inject

class ActorMapper @Inject constructor() {

    fun mapActor(dto: ActorDto): Actor {
        return Actor(
            personId = dto.personId,
            nameRu = dto.nameRu,
            nameEn = dto.nameEn,
            sex = dto.sex,
            posterUrl = dto.posterUrl,
            growth = dto.growth,
            birthday = dto.birthday,
            death = dto.death,
            age = dto.age,
            birthplace = dto.birthplace,
            deathplace = dto.deathplace,
            profession = dto.profession,
            facts = dto.facts,
            films = dto.films.map { mapFilmParticipation(it) }
        )
    }

    private fun mapFilmParticipation(dto: FilmParticipationDto): FilmParticipation {
        return FilmParticipation(
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