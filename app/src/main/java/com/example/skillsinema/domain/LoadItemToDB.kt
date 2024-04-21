package com.example.skillsinema.domain

import android.content.Context
import android.graphics.Bitmap
import com.bumptech.glide.Glide
import com.example.skillsinema.entity.ModelFilmDetails
import com.example.skillsinema.repository.Repository
import javax.inject.Inject

class LoadItemToDB @Inject constructor(
    private val repository: Repository,
    private val context: Context
) {

    suspend fun getItemToDB(id:Int):ItemToDB{

        return repository.getFilmDetails(id).let {

            val bitmap = Glide.with(context)
                .asBitmap()
                .load(it.posterUrlPreview)
                .submit()
                .get()


            ItemToDB(
                id = it.kinopoiskId,
                nameRU = it.nameRu,
                nameEN = it.nameEn,
                poster = bitmap,
                rating = it.ratingAwaitCount)
        }

    }
}

data class ItemToDB (
    var id:Int,
    var nameRU:String?,
    var nameEN:String?,
    var poster:Bitmap?,
    var rating:Int?

)