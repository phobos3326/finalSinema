package com.example.skillsinema.ui.main.ItemInfo

import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skillsinema.DataRepository
import com.example.skillsinema.domain.GetSeasonsUseCase
import com.example.skillsinema.entity.ModelSeasons
import com.example.skillsinema.ui.main.home.MainViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SerialSeasonsViewModel @Inject constructor(
    private val seasonsUseCase: GetSeasonsUseCase,
    private var dataRepository: DataRepository,
) : ViewModel() {

    //private val number = 1
    private var _seasons = MutableStateFlow<List<ModelSeasons.Item>>(emptyList())
    val seasons = _seasons.asStateFlow()

    private var _episodes = MutableStateFlow<List<ModelSeasons.Item.Episode>>(emptyList())
    val episodes = _episodes.asStateFlow()

    private var _serialString = MutableStateFlow<String>("")
    val serialString = _serialString.asStateFlow()

    var number = 0

    init {
        loadEpisodes()
        loadSeasons()
        showEpisodes()
    }

    private fun loadEpisodes() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                seasonsUseCase.getSeasons().items
            }.fold(
                onSuccess = {
                    _episodes.value = it[number].episodes
                },
                onFailure = { Log.d(MainViewModel.TAG, it.message ?: "not load") }
            )
        }
    }


    fun setSeasonNumber(number: Int) {
        this.number = number - 1
        _episodes.value = _seasons.value[number - 1].episodes
        showEpisodes()
        Log.e("onClickseason", number.toString())
    }

    private fun loadSeasons() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                seasonsUseCase.getSeasons()
            }.fold(
                onSuccess = {
                    _seasons.value = it.items
                },
                onFailure = { Log.d(MainViewModel.TAG, it.message ?: "not load") }
            )
        }
    }
    private fun showEpisodes(){
        _serialString.value= "${ _episodes.value[1].seasonNumber} сезон, ${ _episodes.value.size} серий "


    }

}