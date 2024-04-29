package com.example.skillsinema.domain

import android.content.Context
import com.example.skillsinema.dao.InterestedItemEntity
import com.example.skillsinema.dao.InterestedItemPerository
import com.example.skillsinema.entity.ModelActorInfo
import com.example.skillsinema.entity.ModelFilmDetails
import com.example.skillsinema.repository.Repository
import com.example.skillsinema.repository.RepositoryActorInfo
import com.example.skillsinema.ui.main.home.TypeItem
import com.example.skillsinema.ui.main.profile.menu.CollectionsUiModel
import javax.inject.Inject
import kotlin.reflect.typeOf

class LoadItemToDB @Inject constructor(
    private val repository: Repository,
    private val context: Context,
    private val interestedItemPerository: InterestedItemPerository,
    private val repositoryActorInfo: RepositoryActorInfo,
) {

    suspend fun getItemToDB(type: TypeItem, id: Int) {





        /*val bitmap = Glide.with(context)
                .asBitmap()
                .load(it.posterUrlPreview)
                .submit()
                .get()*/


         /* ItemToDB(
                id = it.kinopoiskId,
                nameRU = it.nameRu,
                nameEN = it.nameEn,
             //   poster = bitmap,
                rating = it.ratingAwaitCount)*/

        if (type ==  TypeItem.FILM){
            val modelFilmDetails = repository.getFilmDetails(id)
            interestedItemPerository.insertInterestedItem(
                /*InterestedItemEntity(
                    id = 0,
                    idItem = a.kinopoiskId,
                    typeItem = "film",
                    nameENItem = a.nameEn,
                    nameRUItem = a.nameRu,
                    ratingItem = a.ratingKinopoisk?.toInt()
                )*/

                modelFilmDetails.toItemToDB(type)
            )


        } else if (type == TypeItem.PERSON){
            val modelActorInfo = repositoryActorInfo.getActor(id)
            interestedItemPerository.insertInterestedItem(
                /*InterestedItemEntity(
                    id = 0,
                    idItem = a.kinopoiskId,
                    typeItem = "film",
                    nameENItem = a.nameEn,
                    nameRUItem = a.nameRu,
                    ratingItem = a.ratingKinopoisk?.toInt()
                )*/

                 modelActorInfo.toItemToDB(type )
            )

        }








    }

    fun ModelFilmDetails.toItemToDB(type: TypeItem): InterestedItemEntity {
        return InterestedItemEntity(

            id = 0,
            idItem = kinopoiskId,
            typeItem = type.toString(),
            nameENItem = nameEn,
            nameRUItem = nameRu,
            ratingItem = ratingKinopoisk?.toInt()

        )
    }

    fun ModelActorInfo.toItemToDB(type: TypeItem): InterestedItemEntity {
        return InterestedItemEntity(

            id = 0,
            idItem = personId,
            typeItem = type.toString(),
            nameENItem = nameEn,
            nameRUItem = nameRu,
            ratingItem = 0

        )
    }


}


/*
data class ItemToDB(
    var id: Int,
    var nameRU: String?,
    var nameEN: String?,
    //var poster:Bitmap?,
    var rating: Int?

)*/
