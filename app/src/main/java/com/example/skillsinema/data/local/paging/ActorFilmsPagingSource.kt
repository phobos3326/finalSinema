package com.example.skillsinema.data.local.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.skillsinema.domain.model.FilmParticipation
import com.example.skillsinema.domain.repository.ActorRepository
import java.util.Collections.emptyList

class ActorFilmsPagingSource(
    private val repository: ActorRepository,
    private val actorId: Int
) : PagingSource<Int, FilmParticipation>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, FilmParticipation> {
        return try {
            // Для актеров обычно все фильмы приходят сразу, без пагинации
            val page = params.key ?: 1

            if (page > 1) {
                // Если это не первая страница, возвращаем пустой результат
                return LoadResult.Page(
                    data = emptyList(),
                    prevKey = null,
                    nextKey = null
                )
            }

            val items = repository.getActorFilms(actorId)

            LoadResult.Page(
                data = items,
                prevKey = null,
                nextKey = null // Все данные загружены за один раз
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, FilmParticipation>): Int? {
        return null // Всегда загружаем с первой страницы
    }
}