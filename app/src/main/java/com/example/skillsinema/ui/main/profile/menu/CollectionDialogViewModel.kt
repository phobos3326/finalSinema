package com.example.skillsinema.ui.main.profile.menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skillsinema.DataRepository
import com.example.skillsinema.dao.CollectionEntityRepository
import com.example.skillsinema.dao.CollectionsEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CollectionDialogViewModel @Inject constructor(
    private var collectionEntityRepository: CollectionEntityRepository,
    private var dataRepository: DataRepository,
    private var collectionsUiModel: CollectionsUiModel
) : ViewModel() {

    private val _collection = MutableStateFlow<List<CollectionsEntity>>(emptyList())
    val collection = _collection.asStateFlow()

    private val _id = MutableStateFlow<Int>(0)
    val id = _id.asStateFlow()


    init {
        show()

    }


    fun getValue(): Int {
        _id.value = dataRepository.id
        return dataRepository.id
    }


    fun insertIdtoDB(nameCollection: String){
        viewModelScope.launch() {



        /*    val dbList = collectionEntityRepository.getCollectionList(1).collection
            //val dbbb =

            val mutableDBList = dbList.toMutableList()

            if (!mutableDBList.contains(getValue())) {
                mutableDBList.add(getValue())
            }*/

            /*   if(mutableDBList.contains(99)){

                   mutableDBList.remove(99)
               }*/

            collectionEntityRepository.insertCollection(
                CollectionsEntity(
                    0,
                    nameCollection ,
                    listOf(1)
                )
            )
            withContext(Dispatchers.IO){
                val db = collectionEntityRepository.getAll()
                _collection.value = db
            }
        }
    }

    fun deleteFilmFromDB(colllection: CollectionsEntity){
        viewModelScope.launch(Dispatchers.IO) {
            val db = collectionEntityRepository.getAll()
            val dbList  = collectionEntityRepository.getCollectionList(colllection.collectionName).collection

            val mutableDBList = dbList.toMutableList()

            if (!mutableDBList.contains(getValue())) {
                mutableDBList.remove(getValue())
            }

            collectionEntityRepository.updateCollectionList(colllection.collectionName, mutableDBList)
            _collection.value = db
        }
    }


    fun update(colllection: CollectionsEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            val db = collectionEntityRepository.getAll()
            val dbList  = collectionEntityRepository.getCollectionList(colllection.collectionName).collection

            val mutableDBList = dbList.toMutableList()

            if (!mutableDBList.contains(getValue())) {
                mutableDBList.add(getValue())
            }

            collectionEntityRepository.updateCollectionList(colllection.collectionName, mutableDBList)
            _collection.value = db
        }
    }

    fun show() {
        viewModelScope.launch(Dispatchers.IO) {
            val db = collectionEntityRepository.getAll()
            _collection.value = db
        }
    }




}