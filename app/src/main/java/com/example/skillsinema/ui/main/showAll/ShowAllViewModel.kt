package com.example.skillsinema.ui.main.showAll

import androidx.lifecycle.viewModelScope
import com.example.skillsinema.domain.model.Film
import com.example.skillsinema.domain.usecase.film.GetPremiereFilmsUseCase
import com.example.skillsinema.domain.usecase.film.GetTopFilmsUseCase
import com.example.skillsinema.domain.usecase.film.GetSerialsUseCase
import com.example.skillsinema.domain.usecase.film.GetFilteredFilmsUseCase
import com.example.skillsinema.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.DateFormatSymbols
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ShowAllViewModel @Inject constructor(
    private val getPremieresUseCase: GetPremiereFilmsUseCase,
    private val getTopFilmsUseCase: GetTopFilmsUseCase,
    private val getSerialsUseCase: GetSerialsUseCase,
    private val getFilteredFilmsUseCase: GetFilteredFilmsUseCase
) : BaseViewModel() {

    private val _state = MutableStateFlow(ShowAllUiState())
    val state: StateFlow<ShowAllUiState> = _state.asStateFlow()

    fun loadFilms(category: String, page: Int = 1) { // Добавлен параметр page с дефолтным значением
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)

            runCatching {
                val films = when (category) {
                    "premieres" -> {
                        val calendar = Calendar.getInstance()
                        val monthNumber = calendar.get(Calendar.MONTH)
                        // Приводим название месяца к верхнему регистру
                        val monthName = DateFormatSymbols(Locale.ENGLISH).months[monthNumber].uppercase(Locale.ENGLISH)
                        val year = SimpleDateFormat("yyyy", Locale.getDefault()).format(Date()).toInt()
                        getPremieresUseCase(year, monthName)
                    }
                    "top_films" -> getTopFilmsUseCase()
                    "serials" -> getSerialsUseCase()
                    "filtered" -> getFilteredFilmsUseCase.getFilteredFilms(page) // Передаем параметр page
                    else -> emptyList()
                }

                _state.value = _state.value.copy(
                    films = films,
                    category = category,
                    isLoading = false
                )
            }.onFailure { exception ->
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = exception.message ?: "Ошибка загрузки данных"
                )
            }
        }
    }

    fun refresh(category: String) {
        // При вызове refresh, если нужно, можно передавать актуальную страницу
        // или загружать с первой, как здесь
        loadFilms(category, page = 1)
    }
}

data class ShowAllUiState(
    val films: List<Film> = emptyList(),
    val category: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
)
