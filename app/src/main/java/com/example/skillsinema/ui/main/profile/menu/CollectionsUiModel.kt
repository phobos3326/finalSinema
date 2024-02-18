package com.example.skillsinema.ui.main.profile.menu

import javax.inject.Inject

data class CollectionsUiModel @Inject constructor(
    val id: Int,
    val collectionName:String,
    var collection: List<Int>,
    var isChecked: Boolean,

    ){
    constructor() : this(0, "", listOf(), false)
}
