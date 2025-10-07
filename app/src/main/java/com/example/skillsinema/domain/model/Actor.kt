package com.example.skillsinema.domain.model

import java.util.Collections.emptyList

data class Actor(
    val personId: Int,
    val nameRu: String?,
    val nameEn: String?,
    val sex: String?,
    val posterUrl: String?,
    val growth: String?,
    val birthday: String?,
    val death: String?,
    val age: Int?,
    val birthplace: String?,
    val deathplace: String?,
    val profession: String?,
    val facts: List<String> = emptyList(),
    val films: List<FilmParticipation> = emptyList()
)