package com.example.skillsinema.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.skillsinema.domain.GetStaffUseCase
import com.example.skillsinema.entity.ModelStaff

import javax.inject.Inject

/*
class StaffDataSource @Inject constructor(private val useCase: GetStaffUseCase):PagingSource<Int, ModelStaffItem>() {

    override fun getRefreshKey(state: PagingState<Int, ModelStaffItem>): Int? =FIRST_PAGE

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ModelStaffItem> {
        val page =params.key ?: FIRST_PAGE

        */
/*return kotlin.runCatching {
            useCase.getStaff()
        }.fold(
            onSuccess = {
                LoadResult.Page(
                    data = it,
                    prevKey = null,
                    nextKey = if (it.isEmpty()) null else page+1
                )
            },
            onFailure = {
                LoadResult.Error(it)
            }
        )*//*

    }




    private companion object{
        private val  FIRST_PAGE = 1
    }



}*/
