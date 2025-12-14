package com.example.skillsinema.ui.main.showAll

import androidx.lifecycle.viewModelScope
import com.example.skillsinema.domain.model.Film
import com.example.skillsinema.domain.usecase.GetFilmsByCategoryUseCase
import com.example.skillsinema.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShowAllViewModel @Inject constructor(
    private val getFilmsByCategory: GetFilmsByCategoryUseCase
) : BaseViewModel() {

    private val _state = MutableStateFlow(ShowAllUiState())
    val state: StateFlow<ShowAllUiState> = _state.asStateFlow()

    // ✅ Получаем только категорию, подписываемся на тот же поток!
    fun loadFilms(category: String) {
        _state.value = _state.value.copy(isLoading = true, error = null)

        viewModelScope.launch {
            // Загружаем данные если их еще нет
            getFilmsByCategory.loadCategory(category)

            // Подписываемся на поток - получаем те же данные что и MainFragment
            getFilmsByCategory(category)
                .catch { exception ->
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = exception.message ?: "Ошибка загрузки данных"
                    )
                }
                .collect { films ->
                    _state.value = _state.value.copy(
                        films = films,
                        category = category,
                        isLoading = false
                    )
                }
        }
    }
}

data class ShowAllUiState(
    val films: List<Film> = emptyList(),
    val category: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
)
