package com.example.skillsinema.presentation.ui.showAll

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.skillsinema.domain.model.Film
import com.example.skillsinema.domain.usecase.GetFilmsByCategoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class ShowAllViewModel @Inject constructor(
    private val getFilmsByCategory: GetFilmsByCategoryUseCase
) : ViewModel() {

    fun filmsPaging(category: String): Flow<PagingData<Film>> =
        getFilmsByCategory.paged(category).cachedIn(viewModelScope)
}
