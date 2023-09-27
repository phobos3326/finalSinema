package com.example.skillsinema.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.skillsinema.DataRepository

import com.example.skillsinema.domain.SearchFilmUseCase
import com.example.skillsinema.entity.Film
import javax.inject.Inject

class SearchPagingSource @Inject constructor(
  //  private val getRequest: () -> String,
   // val repository: Repository,
    //val useCase: FiltersUseCase,
    val searchFilmUseCase: SearchFilmUseCase,
    val dataRepository: DataRepository,
    //val query:String

) : PagingSource<Int, Film>() {




    override fun getRefreshKey(state: PagingState<Int, Film>): Int? = FIRST_PAGE

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Film> {


        val page = params.key ?: FIRST_PAGE
       // val word = searchFilmUseCase.getKeyWord(1, "term")
        return kotlin.runCatching {
            //val request = getRequest()
            searchFilmUseCase.getKeyWord(page, dataRepository.keyword)

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