package com.example.skillsinema.ui.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skillsinema.data.Repository
import com.example.skillsinema.domain.GetPremiereUseCase
import com.example.skillsinema.entity.ModelPremiere
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val data: GetPremiereUseCase) : ViewModel() {
    private val _premiereModel = MutableStateFlow<List<ModelPremiere.Item>>(emptyList())
    val modelPremiere = _premiereModel.asStateFlow()

    init {
        viewModelScope.launch {
            loadPremieres()
        }
    }

    private fun loadPremieres() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                data.executeGetPremiere().premiere
            }.fold(
                onSuccess = {
                    _premiereModel.value = it
                    Log.d("MainViewModel", (it ?: " load").toString())
                },
                onFailure = { Log.d("MainViewModel", it.message ?: "not load") }
            )
        }
    }

}