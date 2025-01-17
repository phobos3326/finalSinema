package com.example.skillsinema.ui.main.galerie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.skillsinema.datasource.ShootingGalerieDataSourse
import com.example.skillsinema.datasource.StillGalerieDataSourse
import com.example.skillsinema.datasource.WallpaperGalerieDataSourse
import com.example.skillsinema.entity.ModelGalerie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GalerieViewModel @Inject constructor(
    private val dataSourse: StillGalerieDataSourse,
    private val shootingGalerieDataSourse: ShootingGalerieDataSourse,
    private val wallpaperGalerieDataSourse: WallpaperGalerieDataSourse
) : ViewModel() {

    init {
        viewModelScope.launch {
          pagedFullGalerie
        }
    }


    val pagedFullGalerie: Flow<PagingData<ModelGalerie.Item>> = Pager(
        config = PagingConfig(
            pageSize = 20,
            enablePlaceholders = true

        ),
        pagingSourceFactory = { dataSourse }
    ).flow.cachedIn(viewModelScope)


    val pagesShootingGalerie : Flow<PagingData<ModelGalerie.Item>> = Pager(
        config = PagingConfig(
            pageSize = 20,
            enablePlaceholders = true

        ),
        pagingSourceFactory = { shootingGalerieDataSourse }
    ).flow.cachedIn(viewModelScope)

    val pagesWallpaperGalerie : Flow<PagingData<ModelGalerie.Item>> = Pager(
        config = PagingConfig(
            pageSize = 20,
            enablePlaceholders = true

        ),
        pagingSourceFactory = { wallpaperGalerieDataSourse }
    ).flow.cachedIn(viewModelScope)
}