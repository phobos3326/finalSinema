package com.example.skillsinema

import com.example.skillsinema.domain.FiltersUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import javax.inject.Scope


class DataRepository @Inject constructor(

) {




    var id: Int = 0
    var idActor: Int = 0
    var type_images = ""
    var genreID = 0
    var countryID = 0
    var countryLabel = ""


}