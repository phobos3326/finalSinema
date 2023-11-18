package com.example.skillsinema.dao

import javax.inject.Inject

class WantToSeeFilmRepository @Inject constructor(
    private val wantToSeeFilm: WantToSeeDao) {
    suspend fun insertWantToSeeFilm(itemFilm: WantToSeeFilm){
        wantToSeeFilm.insert(itemFilm)
    }


    fun getAll():List<WantToSeeFilm>{
        return wantToSeeFilm.getAll()
    }

    suspend fun update(itemFilm: WantToSeeFilm){
        wantToSeeFilm.update(itemFilm)
    }

    suspend fun delete(itemFilm: WantToSeeFilm){
        wantToSeeFilm.delete(itemFilm)
    }

}