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
    var countryLabel = ""
    var genreLabel = ""
    var countryID = 0


    var countries: Int? = null
    var countriesName: String? = null
    var genre: Int? = null
    var genreName: String? = null
    var order: String? =null
    var filmType: String? =null

    var ratingFrom: Int? = 0
    var ratingTo: Int? = 10
    var yearFrom: Int? = 1000
    var yearTo: Int? = 3000
    var imdbId: String? =null
    var keyword: String = ""

    var seriesID: Int?   = null


    var rndGenre = 0
    var rndCountry = 0
    var rndCountryLabel = ""
    var rndGenreLabel = ""

}