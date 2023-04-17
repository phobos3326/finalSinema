package com.example.skillsinema.domain

import com.example.skillsinema.data.Repository
import com.example.skillsinema.entity.ModelPremiere

class GetPremiereUseCase {
    private val repository=Repository()
    suspend fun executeGetPremiere():ModelPremiere{
        return repository.getPremiere()
    }
}