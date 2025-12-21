package com.example.skillsinema.domain.model



import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class FilterParams(
    val countries: String? = null,
    val genres: String? = null,
    val order: String? = null,
    val type: String? = null,
    val ratingFrom: Int? = null,
    val ratingTo: Int? = null,
    val yearFrom: Int? = null,
    val yearTo: Int? = null,
    val genreLabel: String? = null, // ✅ Для отображения в UI
    val countryLabel: String? = null, // ✅ Для отображения в UI
    val page: Int = 1
) : Parcelable