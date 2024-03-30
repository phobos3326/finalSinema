package com.example.skillsinema.ui.main.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skillsinema.dao.CollectionDao
import com.example.skillsinema.dao.CollectionEntityRepository
import com.example.skillsinema.dao.CollectionsEntity
import com.example.skillsinema.dao.ItemDao
import com.example.skillsinema.dao.WantToSeeFilm
import com.example.skillsinema.dao.WantToSeeFilmRepository
import com.example.skillsinema.dao.fromJson

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.Reader
import javax.inject.Inject

@HiltViewModel
class ThirdFragmentViewModel @Inject constructor(
    private var wantToSeeFilmRepository: WantToSeeFilmRepository,
    private var collectionEntityRepository: CollectionEntityRepository,
    private val itemDao: CollectionDao
) : ViewModel() {

    private val _collection = MutableStateFlow<List<CollectionsEntity>>(emptyList())
    val collection = _collection.asStateFlow()

    init {
        wantToSee()

    }


    fun addFilmToDB(id: Int) {
        viewModelScope.launch {
            //collectionEntityRepository.
        }
    }


    fun wantToSee() {
        viewModelScope.launch(Dispatchers.IO) {
            val db = collectionEntityRepository.getAll()
            _collection.value = db


        }


    }

    fun deleteCollection(item:CollectionsEntity){
        viewModelScope.launch(Dispatchers.IO) {
            val db = collectionEntityRepository.getAll()
            collectionEntityRepository.delete(item )
            _collection.value = db

            wantToSee()
        }
    }




    companion object {
        val TAG = "ThirdFragmentViewModel"
    }


}