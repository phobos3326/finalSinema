package com.example.skillsinema.ui.main.galerie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.skillsinema.DataRepository
import com.example.skillsinema.domain.model.GalleryImage
import com.example.skillsinema.domain.usecase.gallery.GetGalleryImagesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class GalerieViewModel @Inject constructor(
    private val getGalleryImages: GetGalleryImagesUseCase,
    private val dataRepository: DataRepository
) : ViewModel() {

    private val _filmId = MutableStateFlow(0)
    val filmId = _filmId.asStateFlow()

    fun setFilmId(id: Int) {
        dataRepository.id = id
        _filmId.value = id
    }

    fun getFilmId(): Int = dataRepository.id

    fun getStillFlow(): Flow<PagingData<GalleryImage>> =
        getGalleryImages(dataRepository.id, "STILL").cachedIn(viewModelScope)

    fun getShootingFlow(): Flow<PagingData<GalleryImage>> =
        getGalleryImages(dataRepository.id, "SHOOTING").cachedIn(viewModelScope)

    fun getWallpaperFlow(): Flow<PagingData<GalleryImage>> =
        getGalleryImages(dataRepository.id, "WALLPAPER").cachedIn(viewModelScope)
}
