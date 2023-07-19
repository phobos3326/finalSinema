package com.example.skillsinema.entity


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import javax.annotation.Nonnull
import javax.annotation.Nullable

@JsonClass(generateAdapter = true)
data class ModelActorInfo(
    @Json(name = "age")
    val age: Int,
    @Json(name = "birthday")
    val birthday: String,
    @Json(name = "birthplace")
    val birthplace: String,
    @Json(name = "death")
    val death: Any?,
    @Json(name = "deathplace")
    val deathplace: Any?,
    @Json(name = "facts")
    val facts: List<String>,
    @Json(name = "films")
    val films: List<Film>,
    @Json(name = "growth")
    val growth: Int,
    @Json(name = "hasAwards")
    val hasAwards: Int,
    @Nullable
    @Json(name = "nameEn")
    val nameEn: String?,
    @Json(name = "nameRu")
    val nameRu: String?,
    @Json(name = "personId")
    val personId: Int,
    @Json(name = "posterUrl")
    val posterUrl: String,
    @Json(name = "profession")
    val profession: String,
    @Json(name = "sex")
    val sex: String,
    @Json(name = "spouses")
    val spouses: List<Spouse>,
    @Json(name = "webUrl")
    val webUrl: String
) {
    @JsonClass(generateAdapter = true)
    data class Film(
        @Json(name = "description")
        val description: String,
        @Json(name = "filmId")
        val filmId: Int,
        @Json(name = "general")
        val general: Boolean,
        @Json(name = "nameEn")
        val nameEn: String?,
        @Json(name = "nameRu")
        val nameRu: String?,
        @Json(name = "professionKey")
        val professionKey: String,
        @Json(name = "rating")
        val rating: String?
    )

    @JsonClass(generateAdapter = true)
    data class Spouse(
        @Json(name = "children")
        val children: Int,
        @Json(name = "divorced")
        val divorced: Boolean,
        @Json(name = "divorcedReason")
        val divorcedReason: String,
        @Json(name = "name")
        val name: String,
        @Json(name = "personId")
        val personId: Int,
        @Json(name = "relation")
        val relation: String,
        @Json(name = "sex")
        val sex: String,
        @Json(name = "webUrl")
        val webUrl: String
    )
}