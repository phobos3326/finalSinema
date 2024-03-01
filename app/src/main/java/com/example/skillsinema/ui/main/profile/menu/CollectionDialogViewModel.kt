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
import org.modelmapper.ModelMapper
import org.modelmapper.convention.MatchingStrategies
import org.modelmapper.spi.MatchingStrategy
import javax.inject.Inject

@HiltViewModel
class CollectionDialogViewModel @Inject constructor(
    private var collectionEntityRepository: CollectionEntityRepository,
    private var dataRepository: DataRepository,
    private var collectionsUiModel: CollectionsUiModel
) : ViewModel() {

    private val _collection = MutableStateFlow<List<CollectionsEntity>>(emptyList())
    val collection = _collection.asStateFlow()


    private val _collectionUi = MutableStateFlow<List<CollectionsUiModel>>(emptyList())
    val collectionUi = _collectionUi.asStateFlow()

    private val _id = MutableStateFlow<Int>(0)
    val id = _id.asStateFlow()


    init {
        show()

    }


    fun getValue(): Int {
        _id.value = dataRepository.id
        return dataRepository.id
    }


    fun insertIdtoDB(nameCollection: String) {
        viewModelScope.launch() {


            collectionEntityRepository.insertCollection(
                CollectionsEntity(
                    0,
                    nameCollection,
                    emptyList()
                )

            )

            withContext(Dispatchers.IO) {
                val db = collectionEntityRepository.getAll()

                _collection.value = db
                _collection.emit(db)

            }

        }

    }

    fun deleteFilmFromDB(colllection: CollectionsEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            val db = collectionEntityRepository.getAll()
            val dbList =
                collectionEntityRepository.getCollectionList(colllection.collectionName).collection

            val mutableDBList = dbList?.toMutableList()

            if (!mutableDBList?.contains(getValue())!!) {
                mutableDBList?.remove(getValue())
            }

            collectionEntityRepository.updateCollectionList(
                colllection.collectionName,
                mutableDBList
            )
            _collection.value = db
        }
    }


    fun update(uiModel: CollectionsUiModel) {
        viewModelScope.launch(Dispatchers.IO) {
            var updatedCollection = uiModel.collection?.toMutableList()
            var entity = mapUiModelToEntity(uiModel)
            if (uiModel.collection?.isEmpty() ?: true && !uiModel.isChecked) {
                updatedCollection = mutableListOf()
                updatedCollection?.add(getValue())
                val updatedModel = uiModel.copy(collection = updatedCollection, isChecked = true)
                entity = mapUiModelToEntity(updatedModel)
                collectionEntityRepository.updateCollectionList(
                    entity.collectionName,
                    entity.collection
                )

                show()
            } else if (uiModel.collection?.contains(getValue()) != true && !uiModel.isChecked) {
                updatedCollection?.add(getValue())
                val updatedModel = uiModel.copy(collection = updatedCollection, isChecked = true)
                entity = mapUiModelToEntity(updatedModel)
                collectionEntityRepository.updateCollectionList(
                    entity.collectionName,
                    entity.collection
                )

                show()
            } else {
                updatedCollection?.remove(getValue())
                val updatedModel = uiModel.copy(collection = updatedCollection, isChecked = false)
                entity = mapUiModelToEntity(updatedModel)
                collectionEntityRepository.updateCollectionList(
                    entity.collectionName,
                    entity.collection

                )

                show()
            }
        }


    }

    private fun mapUiModelToEntity(uiModel: CollectionsUiModel): CollectionsEntity {
        return CollectionsEntity(
            uiModel.id,
            uiModel.collectionName,
            uiModel.collection,

            )
    }

    /* fun update(colllection: CollectionsEntity) {
         viewModelScope.launch(Dispatchers.IO) {
             val db = collectionEntityRepository.getAll()
             val dbList =
                 collectionEntityRepository.getCollectionList(colllection.collectionName).collection

             val mutableDBList = dbList?.toMutableList()

             if (!mutableDBList?.contains(getValue())!!) {
                 mutableDBList?.add(getValue())
             }

             collectionEntityRepository.updateCollectionList(
                 colllection.collectionName,
                 mutableDBList
             )
             _collection.value = db
         }
     }*/

    private fun show() {
        viewModelScope.launch(Dispatchers.IO) {
            val db = collectionEntityRepository.getAll()
            var UIModelList = emptyList<CollectionsUiModel>().toMutableList()
            if (UIModelList.isEmpty()) {
                UIModelList = mutableListOf()
            }
            db.forEach {
                UIModelList.add(it.toUiModel())

            }
            val colllectionList = listOf<Int>().toMutableList()

            UIModelList.forEach { collectionsUiModel ->
                if (collectionsUiModel.collection?.contains(getValue()) == true)
                    collectionsUiModel.isChecked = true

            }
            _collectionUi.value = UIModelList
        }
    }

    fun toDB() {

    }

    fun CollectionsEntity.toUiModel(isChecked: Boolean = false): CollectionsUiModel {
        return CollectionsUiModel(
            id = this.id,
            collectionName = this.collectionName,
            collection = this.collection,
            isChecked = isChecked
        )
    }


    fun CollectionsUiModel.toCollectionEntity(isChecked: Boolean = false): CollectionsEntity {
        return CollectionsEntity(
            id = this.id,
            collectionName = this.collectionName,
            collection = this.collection

        )
    }

}