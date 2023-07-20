package com.example.skillsinema.domain

import com.example.skillsinema.DataRepository
import com.example.skillsinema.entity.Film
import com.example.skillsinema.repository.Repository
import com.example.skillsinema.repository.RepositoryActorInfo
import javax.inject.Inject

class GetActorFilmUseCase @Inject constructor(
    private val repositoryActorInfo: RepositoryActorInfo,
    private val repository: Repository,
    private val dataRepository: DataRepository
) {
    suspend fun getActorFilm(): List<Film> {
        var id = dataRepository.idActor
        val list:MutableList<Film> = repositoryActorInfo.getActorFilm(id) as MutableList<Film>
        val listFilm = mutableListOf<Film>()

        list.forEachIndexed { index, film ->

            //listFilm[index].posterUrlPreview = list[index].posterUrlPreview
            list[index].posterUrlPreview=repository.getFilmDetails(film.filmId!!).posterUrlPreview

        }
        return list

    }
}