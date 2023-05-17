package com.example.skillsinema.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class SharedViewModel:ViewModel() {
    private val _data =MutableLiveData<String>()
    val data:LiveData<String> get() = _data

    fun updateData(value:String){
        viewModelScope.launch {
            _data.value=value
        }
    }
}