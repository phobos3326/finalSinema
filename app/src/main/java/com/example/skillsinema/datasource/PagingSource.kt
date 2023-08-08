package com.example.skillsinema.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.skillsinema.entity.Film
import com.example.skillsinema.repository.MoviePagedListRepository
import javax.inject.Inject

class FilmPagingSourse @Inject constructor(val repository: MoviePagedListRepository) :
    PagingSource<Int, Film>() {

    override fun getRefreshKey(state: PagingState<Int, Film>): Int? = FIRST_PAGE

   /* override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }*/

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Film> {
        val page = params.key ?: FIRST_PAGE
       return kotlin.runCatching {
            repository.getTopFilm(page)
        }.fold(
            onSuccess = {
                LoadResult.Page(
                    data = it,
                    prevKey = null,
                   // nextKey =  null
                   nextKey = if (it.isEmpty()) null else page+1
                )
            },
            onFailure = {
                LoadResult.Error(it)
            }
        )
    }






    private companion object{
        private val  FIRST_PAGE = 1
    }



}