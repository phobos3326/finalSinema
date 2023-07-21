package com.example.skillsinema.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.skillsinema.DataRepository
import com.example.skillsinema.data.FilmPagingSourse
import com.example.skillsinema.datasource.BestActorFilmPagingSource.Companion.FIRST_PAGE
import com.example.skillsinema.domain.GetActorFilmUseCase
import com.example.skillsinema.entity.Film
import com.example.skillsinema.repository.RepositoryActorInfo
import javax.inject.Inject

class BestActorFilmPagingSource @Inject constructor(
    private val dataRepository: DataRepository,
    private val repositoryActorInfo: RepositoryActorInfo,
    private val useCase: GetActorFilmUseCase
):PagingSource<Int, Film>() {
    override fun getRefreshKey(state: PagingState<Int, Film>): Int? = FIRST_PAGE

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Film> {
       // val page = params.key ?: FilmPagingSourse.FIRST_PAGE

        return kotlin.runCatching {
            useCase.getActorFilm()
        }.fold(
            onSuccess = {
                LoadResult.Page(
                    data=it,
                    prevKey = null,
                    nextKey = null
                )
            },
            onFailure = {
                LoadResult.Error(it)
            }

        )
    }



    private companion object {
        private val FIRST_PAGE = 1
    }

}