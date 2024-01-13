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

            val dbList = collectionEntityRepository.getCollectionList(82).collection
            //val dbbb =

            val mutableDBList = dbList.toMutableList()

            if (!mutableDBList.contains(6)) {
                mutableDBList.add(6)
            }

         /*   if(mutableDBList.contains(99)){

                mutableDBList.remove(99)
            }*/

                collectionEntityRepository.insertCollection(
                    CollectionsEntity(
                        82,
                        "name",
                        mutableDBList
                    )
                )


            //collectionEntityRepository.updateCollectionList (1,  mutableDBList)


            Log.d(TAG, "list_____ $dbList")

            _collection.value = db

            /*Log.d(TAG, "list ${db}")

            for (i in db) {
                Log.d(TAG, "list ${i.id}, ${i.collection.joinToString(",")}, ${i.collectionName}")
            }*/


        }


    }


    companion object {
        val TAG = "ThirdFragmentViewModel"
    }


}