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
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
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

    fun wantToSee() {
        viewModelScope.launch(Dispatchers.IO) {
            val db = collectionEntityRepository.getAll()
            _collection.value = db

            //Log.d(TAG, "list ${db}")

            for (i in db) {
                Log.d(TAG, "list ${i.id}, ${i.collection.joinToString(",")}, ${i.collectionName}")
            }


        }


    }


    companion object {
        val TAG = "ThirdFragmentViewModel"
    }


}