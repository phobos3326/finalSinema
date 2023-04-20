package com.example.skillsinema.domain

import com.example.skillsinema.adapter.ModelPremiereDTO
import com.example.skillsinema.data.Repository
import com.example.skillsinema.entity.ModelPremiere
import javax.inject.Inject

class GetPremiereUseCase @Inject constructor(private val repository: Repository) {
        suspend fun executeGetPremiere():ModelPremiere{
        return repository.getPremiere()
    }
}