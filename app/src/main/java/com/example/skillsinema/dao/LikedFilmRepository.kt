package com.example.skillsinema.dao

import javax.inject.Inject

class LikedFilmRepository @Inject constructor(
    private val likedFilmDao: LikedFilmDao
) {
    suspend fun insertLikedFilm(itemFilm: LikedFilms){
        likedFilmDao.insert(itemFilm)
    }

    fun getAll(): List<LikedFilms> {
        return likedFilmDao.getAll()
    }

    suspend fun update(itemFilm: LikedFilms){
        likedFilmDao.update(itemFilm)
    }

    suspend fun delete(itemFilm: LikedFilms){
        likedFilmDao.delete(itemFilm)
    }

}