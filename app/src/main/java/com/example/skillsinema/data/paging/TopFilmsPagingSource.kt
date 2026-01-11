package com.example.skillsinema.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.skillsinema.data.api.KinopoiskApi
import com.example.skillsinema.data.mapper.FilmMapper
import com.example.skillsinema.domain.model.Film

class TopFilmsPagingSource(
    private val api: KinopoiskApi,
    private val filmMapper: FilmMapper
) : PagingSource<Int, Film>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Film> {
        val page = params.key ?: 1
        return try {
            val resp = api.getTopFilms(type = "TOP_250_BEST_FILMS", page = page)

            if (!resp.isSuccessful) return LoadResult.Error(Exception(resp.message()))
            val body = resp.body()
            val data = filmMapper.mapFilmList(body?.films.orEmpty())

            val nextKey = if (body != null && page < body.pagesCount) page + 1 else null

            LoadResult.Page(
                data = data,
                prevKey = if (page == 1) null else page - 1,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Film>): Int? {
        return state.anchorPosition?.let { anchor ->
            state.closestPageToPosition(anchor)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchor)?.nextKey?.minus(1)
        }
    }
}
