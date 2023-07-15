package com.example.skillsinema.datasource


import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.skillsinema.entity.Film
import com.example.skillsinema.repository.Repository
import javax.inject.Inject

class FilteredFilmPagingSource @Inject constructor(
    val repository: Repository,
    val dataSource: FiltersDataSource
) :
    PagingSource<Int, Film>() {

    var genre = 0
    var countries = 1
    override fun getRefreshKey(state: PagingState<Int, Film>): Int? = FIRST_PAGE

    /* override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
         return state.anchorPosition?.let {
             state.closestPageToPosition(it)?.prevKey?.plus(1)
                 ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
         }
     }*/

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Film> {

        dataSource.loadFilters().forEach {
            //countries = it.countries[1].id
            genre = it.id
        }
        val page = params.key ?: FIRST_PAGE

        return kotlin.runCatching {
            repository.getFilteredFilm(page, 1, 11)
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