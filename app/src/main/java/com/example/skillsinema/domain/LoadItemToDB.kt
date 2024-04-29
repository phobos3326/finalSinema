package com.example.skillsinema.domain

import android.content.Context
import android.graphics.Bitmap
import com.bumptech.glide.Glide
import com.example.skillsinema.dao.InterestedItemEntity
import com.example.skillsinema.dao.InterestedItemPerository
import com.example.skillsinema.entity.ModelActorInfo
import com.example.skillsinema.entity.ModelFilmDetails
import com.example.skillsinema.repository.Repository
import com.example.skillsinema.repository.RepositoryActorInfo
import com.example.skillsinema.ui.main.home.TypeItem
import com.example.skillsinema.ui.main.profile.menu.CollectionsUiModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import javax.inject.Inject
import kotlin.reflect.typeOf

class LoadItemToDB @Inject constructor(
    private val repository: Repository,
    private val context: Context,
    private val interestedItemPerository: InterestedItemPerository,
    private val repositoryActorInfo: RepositoryActorInfo,
) {

    suspend fun getItemToDB(type: TypeItem, id: Int) {


        CoroutineScope(Dispatchers.IO).launch {
            if (type == TypeItem.FILM) {
                val modelFilmDetails = repository.getFilmDetails(id)

                val bitmap = Glide.with(context)
                    .asBitmap()
                    .load(modelFilmDetails.posterUrlPreview)
                    .submit()
                    .get()

                val byteArrayOutputStream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
                val imageBytes = byteArrayOutputStream.toByteArray()


                interestedItemPerository.insertInterestedItem(
                    modelFilmDetails.toItemToDB(type, imageBytes)
                )


            } else if (type == TypeItem.PERSON) {
                val modelActorInfo = repositoryActorInfo.getActor(id)

                val bitmap = Glide.with(context)
                    .asBitmap()
                    .load(modelActorInfo.posterUrl)
                    .submit()
                    .get()

                val byteArrayOutputStream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
                val imageBytes = byteArrayOutputStream.toByteArray()

                interestedItemPerository.insertInterestedItem(
                    modelActorInfo.toItemToDB(type, imageBytes)
                )

            }

        }
    }

    private fun ModelFilmDetails.toItemToDB(
        type: TypeItem,
        poster: ByteArray
    ): InterestedItemEntity {
        return InterestedItemEntity(
            id = kinopoiskId,
            idItem = kinopoiskId,
            typeItem = type.toString(),
            nameENItem = nameEn,
            nameRUItem = nameRu,
            posterItem = poster,
            ratingItem = ratingKinopoisk?.toInt()
        )
    }

    fun ModelActorInfo.toItemToDB(type: TypeItem, poster: ByteArray): InterestedItemEntity {
        return InterestedItemEntity(
            id = personId,
            idItem = personId,
            typeItem = type.toString(),
            nameENItem = nameEn,
            nameRUItem = nameRu,
            posterItem = poster,
            ratingItem = 0

        )
    }


}


