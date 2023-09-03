package com.example.skillsinema.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.skillsinema.DataRepository
import com.example.skillsinema.domain.FilteredFilmsUseCase
import com.example.skillsinema.domain.FiltersUseCase

import com.example.skillsinema.domain.searchFilmUseCase
import com.example.skillsinema.entity.Film
import com.example.skillsinema.repository.MoviePagedListRepository
import com.example.skillsinema.repository.Repository
import javax.inject.Inject

class SearchPagingSource @Inject constructor(
    val repository: Repository,
    //val dataSource: FiltersDataSource,
    //val dataRepository: DataRepository,
    val useCase: FiltersUseCase,
    val searchFilmUseCase: searchFilmUseCase,
    val dataRepository: DataRepository

) :     PagingSource<Int, Film>() {


    override fun getRefreshKey(state: PagingState<Int, Film>): Int? = FIRST_PAGE

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Film> {



        val page = params.key ?: FIRST_PAGE

        return kotlin.runCatching {
            searchFilmUseCase.getKeyWord(page)
        }.fold(
            onSuccess = {
                LoadResult.Page(
                    data = it,
                    prevKey = null,
                   // nextKey =  null
                    nextKey = if (it.isEmpty()) null else page + 1
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