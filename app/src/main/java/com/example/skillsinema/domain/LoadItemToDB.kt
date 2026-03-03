package com.example.skillsinema.domain

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import com.bumptech.glide.Glide
import com.example.skillsinema.dao.InterestedItemEntity
import com.example.skillsinema.dao.InterestedItemPerository
import com.example.skillsinema.entity.ModelActorInfo
import com.example.skillsinema.entity.ModelFilmDetails
import com.example.skillsinema.repository.Repository
import com.example.skillsinema.repository.RepositoryActorInfo
import com.example.skillsinema.presentation.ui.model.TypeItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import javax.inject.Inject

class LoadItemToDB @Inject constructor(
    private val repository: Repository,
    private val context: Context,
    private val interestedItemPerository: InterestedItemPerository,
    private val repositoryActorInfo: RepositoryActorInfo,
) {

    suspend fun getItemToDB(type: TypeItem, id: Int) = withContext(Dispatchers.IO) {
        try {
            if (type == TypeItem.FILM) {
                val modelFilmDetails = repository.getFilmDetails(id)
                Log.d("LoadItemToDB", "Film: ${modelFilmDetails.nameRu}, posterUrl: ${modelFilmDetails.posterUrlPreview}")

                val bitmap = Glide.with(context)
                    .asBitmap()
                    .load(modelFilmDetails.posterUrlPreview)
                    .submit()
                    .get()

                val byteArrayOutputStream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
                val imageBytes = byteArrayOutputStream.toByteArray()
                Log.d("LoadItemToDB", "Image size: ${imageBytes.size} bytes")

                val entity = modelFilmDetails.toItemToDB(type, imageBytes)
                interestedItemPerository.insertInterestedItem(entity)
                Log.d("LoadItemToDB", "Inserted to DB: ${entity.nameRUItem}")

            } else if (type == TypeItem.PERSON) {
                val modelActorInfo = repositoryActorInfo.getActor(id)
                Log.d("LoadItemToDB", "Actor: ${modelActorInfo.nameRu}")

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
                Log.d("LoadItemToDB", "Inserted actor to DB")
            }
        } catch (e: Exception) {
            Log.e("LoadItemToDB", "Error saving to DB: ${e.message}", e)
        }
    }

    private fun ModelFilmDetails.toItemToDB(
        type: TypeItem,
        poster: ByteArray
    ): InterestedItemEntity {
        return InterestedItemEntity(
            id = kinopoiskId,
            idItem = kinopoiskId,
            typeItem = type,
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
            typeItem = type,
            nameENItem = nameEn,
            nameRUItem = nameRu,
            posterItem = poster,
            ratingItem = 0

        )
    }
}
