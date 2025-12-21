package com.example.skillsinema.domain.usecase

import com.example.skillsinema.domain.model.Country
import com.example.skillsinema.domain.model.FilterParams
import com.example.skillsinema.domain.model.Genre
import com.example.skillsinema.domain.repository.FilmRepository
import javax.inject.Inject
import kotlin.random.Random

class GetRandomFiltersUseCase @Inject constructor(
    private val filmRepository: FilmRepository
) {
    suspend operator fun invoke(): FilterParams {
        // ✅ Получаем все жанры и страны
        val filtersResponse = filmRepository.getFilters()

        val genres = filtersResponse.genres.take(17) // Безопасно берем первые 17
        val countries = filtersResponse.countries.take(34) // Безопасно берем первые 34

        // ✅ Генерируем случайные индексы
        val randomGenre = genres.randomOrNull() ?: genres.first()
        val randomCountry = countries.randomOrNull() ?: countries.first()

        // ✅ Возвращаем готовые параметры
        return FilterParams(
            genres = randomGenre.id.toString(),
            countries = randomCountry.id.toString(),
            order = "RATING", // По умолчанию сортировка по рейтингу
            type = "FILM",
            ratingFrom = 5,
            ratingTo = 10,
            yearFrom = 2000,
            yearTo = 2024,
            genreLabel = randomGenre.genre, // Для отображения
            countryLabel = randomCountry.country // Для отображения
        )
    }
}
