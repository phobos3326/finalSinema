package com.example.skillsinema.adapter

import com.example.skillsinema.entity.ModelPremiere
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
interface ModelPremiereDTO {
    val premiere:List<ModelPremiere.Item>
}