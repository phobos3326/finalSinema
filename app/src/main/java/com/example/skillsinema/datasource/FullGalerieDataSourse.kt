package com.example.skillsinema.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.skillsinema.domain.GalerieUseCase
import com.example.skillsinema.entity.ModelGalerie
import com.example.skillsinema.repository.GalerieRepository
import javax.inject.Inject

class FullGalerieDataSourse @Inject constructor(
    val repository: GalerieRepository,
    val useCase: GalerieUseCase
) : PagingSource<Int, ModelGalerie.Item>() {


    override fun getRefreshKey(state: PagingState<Int, ModelGalerie.Item>): Int? = FIRST_PAGE

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ModelGalerie.Item> {

        val page = params.key ?: FIRST_PAGE
        return kotlin.runCatching {
            useCase.getGalerie(page)
        }.fold(
            onSuccess = {
                LoadResult.Page(
                    data = it!!,
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