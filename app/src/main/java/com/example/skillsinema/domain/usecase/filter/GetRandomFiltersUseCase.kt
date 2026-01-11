package com.example.skillsinema.domain.usecase.filter

import com.example.skillsinema.domain.repository.FilterRepository
import javax.inject.Inject

data class RandomFilters(
    val countryId: Int?,
    val genreId: Int?
)

class GetRandomFiltersUseCase @Inject constructor(
    private val repository: FilterRepository
) {
    suspend operator fun invoke(): RandomFilters {
        val filters = repository.getFilters() // уже НЕ Response

        val randomGenreId: Int? = filters.genres.randomOrNull()?.id
        val randomCountryId: Int? = filters.countries.randomOrNull()?.id

        return RandomFilters(
            countryId = randomCountryId,
            genreId = randomGenreId
        )
    }
}
