package com.example.skillsinema.dao

import javax.inject.Inject

class LikedFilmRepository @Inject constructor(
    private val likedFilmDao: LikedFilmDao
) {
    suspend fun insertLikedFilm(itemFilm: LikedFilms){
        likedFilmDao.insert(itemFilm)
    }

    suspend fun update(itemFilm: LikedFilms){
        likedFilmDao.update(itemFilm)
    }

}