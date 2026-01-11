package com.example.skillsinema.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.skillsinema.data.api.KinopoiskApi
import com.example.skillsinema.data.mapper.FilmMapper
import com.example.skillsinema.domain.model.Film

class PremieresPagingSource(
    private val api: KinopoiskApi,
    private val filmMapper: FilmMapper,
    private val year: Int,
    private val month: String
) : PagingSource<Int, Film>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Film> {
        val page = params.key ?: 1
        return try {
            // В API premieres обычно без page, поэтому имитируем: первая страница = все элементы, дальше пусто.
            val resp = api.getPremieres(year, month)

            if (!resp.isSuccessful) return LoadResult.Error(Exception(resp.message()))
            val body = resp.body()
            val data = filmMapper.mapFilmList(body?.items.orEmpty())

            LoadResult.Page(
                data = data,
                prevKey = null,
                nextKey = null // premieres отдают список целиком, пагинации нет
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Film>): Int? = null
}
