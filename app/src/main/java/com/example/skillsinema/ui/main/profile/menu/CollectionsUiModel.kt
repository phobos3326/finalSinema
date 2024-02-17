package com.example.skillsinema.ui.main.profile.menu

import javax.inject.Inject

data class CollectionsUiModel @Inject constructor(
    val id: Int,
    val collectionName: String,
    val collection: List<Int>,
    val isChecked: Boolean,
)
