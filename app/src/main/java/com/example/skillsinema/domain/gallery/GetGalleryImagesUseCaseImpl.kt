package com.example.skillsinema.domain.gallery

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.skillsinema.datasource.GalerieDataSource
import com.example.skillsinema.entity.ModelGalerie
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetGalleryImagesUseCaseImpl @Inject constructor(
    private val galerieDataSourceFactory: GalerieDataSourceFactory
) : GetGalleryImagesUseCase {

    override fun invoke(filmId: Int, imageType: String): Flow<PagingData<ModelGalerie.Item>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = true,
                prefetchDistance = 5,
                initialLoadSize = 20
            ),
            pagingSourceFactory = {
                galerieDataSourceFactory.create(filmId, imageType)
            }
        ).flow
    }
}
