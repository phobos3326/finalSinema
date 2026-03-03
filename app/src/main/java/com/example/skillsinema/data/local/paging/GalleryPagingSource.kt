package com.example.skillsinema.data.local.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.skillsinema.domain.model.GalleryImage
import com.example.skillsinema.domain.repository.GalleryRepository

class GalleryPagingSource(
    private val repository: GalleryRepository,
    private val filmId: Int,
    private val imageType: String
) : PagingSource<Int, GalleryImage>() {

    private val seenImageUrls = mutableSetOf<String>()

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GalleryImage> {
        return try {
            val page = params.key ?: 1

            val items = repository.getGallery(filmId, imageType)
            
            // Фильтрация дубликатов по imageUrl
            val uniqueItems = items.filter { image ->
                if (image.imageUrl in seenImageUrls) {
                    false
                } else {
                    seenImageUrls.add(image.imageUrl)
                    true
                }
            }

            LoadResult.Page(
                data = uniqueItems,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (uniqueItems.isEmpty()) null else page + 1
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, GalleryImage>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val anchorPage = state.closestPageToPosition(anchorPosition)
        return anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
    }
}