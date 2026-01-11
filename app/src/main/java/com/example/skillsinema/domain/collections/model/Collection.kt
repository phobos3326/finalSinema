package com.example.skillsinema.domain.collections.model

data class Collection(
    val id: Int,
    val name: String,
    val filmIds: List<Int>
)
