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
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SerialSeasonsViewModel @Inject constructor(
    private val seasonsUseCase: GetSeasonsUseCase,
    private var dataRepository: DataRepository,
) : ViewModel() {
    var number = 0

    //private val number = 1
    private var _seasons = MutableStateFlow<List<ModelSeasons.Item>>(emptyList())
    val seasons = _seasons.asStateFlow()

    private var _episodes = MutableStateFlow<List<ModelSeasons.Item.Episode>>(emptyList())
    val episodes = _episodes.asStateFlow()

//    val string = "${ episodes.value[number].seasonNumber} сезон, ${ episodes.value.size} серий "

    private var _serialString = MutableStateFlow<String>("")
    val serialString = _serialString.asStateFlow()


    init {
        loadEpisodes()
        loadSeasons()
        viewModelScope.launch {
            //
        }
        showEpisodes()
        //setSeasonNumber(1)
    }

    private fun loadEpisodes() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                seasonsUseCase.getSeasons().items
            }.fold(
                onSuccess = {
                    _episodes.value = it[number].episodes
                    //   _episodes.value = _seasons.value[number ].episodes
                    showEpisodes()

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

    fun showEpisodes() {
        if (episodes.value.isEmpty()) {
            _serialString.value = ""

        } else {
            _serialString.value =
                "${episodes.value[number].seasonNumber} сезон, ${episodes.value.size} серий "
        }


    }

}