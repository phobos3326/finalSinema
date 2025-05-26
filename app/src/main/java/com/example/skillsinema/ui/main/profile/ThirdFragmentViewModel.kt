package com.example.skillsinema.ui.main.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skillsinema.dao.CollectionDao
import com.example.skillsinema.dao.CollectionEntityRepository
import com.example.skillsinema.dao.CollectionsEntity
import com.example.skillsinema.dao.InterestedItemEntity
import com.example.skillsinema.dao.InterestedItemPerository
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
    private val itemDao: CollectionDao,
    private val interestedItemPerository: InterestedItemPerository
) : ViewModel() {

    private val _collection = MutableStateFlow<List<CollectionsEntity>>(emptyList())
    val collection = _collection.asStateFlow()

    private val _interestedCollection = MutableStateFlow<List<InterestedItemEntity>>(emptyList())
    val interestedCollection = _interestedCollection.asStateFlow()


    init {
        wantToSee()

        showInterestedCollection()
    }


    fun addFilmToDB(id: Int) {
        viewModelScope.launch {
         //   collectionEntityRepository.
        }
    }

    fun showInterestedCollection() {

        viewModelScope.launch(Dispatchers.IO) {
            val interestedItem = interestedItemPerository.getAll()
            _interestedCollection.value = interestedItem

        }

    }


    fun wantToSee() {
        viewModelScope.launch(Dispatchers.IO) {
            val db = collectionEntityRepository.getAll()
            _collection.value = db


        }


    }

    fun deleteCollection(item: CollectionsEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            val db = collectionEntityRepository.getAll()
            collectionEntityRepository.delete(item)
            _collection.value = db

            wantToSee()
        }
    }


    companion object {
        val TAG = "ThirdFragmentViewModel"
    }


}