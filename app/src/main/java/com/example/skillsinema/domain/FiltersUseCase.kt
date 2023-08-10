package com.example.skillsinema.domain

import com.example.skillsinema.DataRepository
import com.example.skillsinema.entity.ModelFilter
import com.example.skillsinema.repository.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import retrofit2.Response

import javax.inject.Inject

class FiltersUseCase @Inject constructor(
    private val repository: Repository,
    private val dataRepository: DataRepository
) {


    suspend fun getFilters(): Response<ModelFilter> {
        return repository.getFilters()
    }

    /*    suspend fun getFiltersGenre(): List<ModelFilter.Genre> {
           return repository.getFilters().genres

           *//* val genreIdRNd = (0..genreList.size-1).random()
        val genrieLabel = genreList[genreIdRNd].genre

        dataRepository.genreID = genrieLabel*//*
        //return genreIdRNd
    }

    suspend fun getFiltersCountries(): List<ModelFilter.Country> {

        return  repository.getFilters().countries
        *//*val countrieIdRNd = (0..countrieList.size-1).random()
        val countrieLabel = countrieList[countrieIdRNd].country

        dataRepository.countryID = countrieLabel
        return countrieIdRNd*//*
    }*/

}