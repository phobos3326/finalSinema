package com.example.skillsinema.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.skillsinema.domain.model.GalleryImage
import com.example.skillsinema.domain.repository.GalleryRepository

class GalleryPagingSource(
    private val repository: GalleryRepository,
    private val filmId: Int,
    private val imageType: String
) : PagingSource<Int, GalleryImage>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GalleryImage> {
        return try {
            val page = params.key ?: 1

            val items = repository.getGallery(filmId, imageType)

            LoadResult.Page(
                data = items,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (items.isEmpty()) null else page + 1
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, GalleryImage>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}