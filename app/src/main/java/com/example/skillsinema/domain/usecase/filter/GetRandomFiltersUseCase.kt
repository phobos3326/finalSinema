package com.example.skillsinema.domain.usecase.filter

import com.example.skillsinema.domain.model.Genre
import com.example.skillsinema.domain.model.Country
import com.example.skillsinema.domain.model.FilterParams
import com.example.skillsinema.domain.repository.FilterRepository
import javax.inject.Inject

class GetRandomFiltersUseCase @Inject constructor(
    private val filterRepository: FilterRepository
) {
    suspend operator fun invoke(): FilterParams {
        val filters = filterRepository.getFilters().body()

        val genres = filters?.genres?.take(17) ?: emptyList()
        val countries = filters?.countries?.take(34) ?: emptyList()

        val randomGenre = genres.randomOrNull()
        val randomCountry = countries.randomOrNull()

        return FilterParams(
            countries = randomCountry?.id?.toString(),
            genres = randomGenre?.id?.toString()
        )
    }
}