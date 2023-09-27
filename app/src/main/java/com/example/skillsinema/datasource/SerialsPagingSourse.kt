package com.example.skillsinema.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.skillsinema.domain.GetSerialsUseCase
import com.example.skillsinema.entity.Film
import javax.inject.Inject

class SerialsPagingSourse @Inject constructor(
    private val getSerialsUseCase: GetSerialsUseCase,
) : PagingSource<Int, Film>() {

    override fun getRefreshKey(state: PagingState<Int, Film>): Int? = FIRST_PAGE

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Film> {
        val page = params.key ?: FIRST_PAGE

        return kotlin.runCatching {
            getSerialsUseCase.getSerials(page)
        }.fold(
            onSuccess = {
                LoadResult.Page(
                    data = it,
                    prevKey = null,
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