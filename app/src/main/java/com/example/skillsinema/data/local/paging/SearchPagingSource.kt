package com.example.skillsinema.data.local.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.skillsinema.domain.model.Film
import com.example.skillsinema.domain.repository.SearchRepository
import kotlinx.coroutines.delay

class SearchPagingSource(
    private val repository: SearchRepository,
    private val query: String
) : PagingSource<Int, Film>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Film> {
        return try {
            val page = params.key ?: 1

            if (page > 1) {
                delay(300)
            }

            val items = repository.searchFilms(query, page)

            LoadResult.Page(
                data = items,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (items.isEmpty()) null else page + 1
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Film>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}