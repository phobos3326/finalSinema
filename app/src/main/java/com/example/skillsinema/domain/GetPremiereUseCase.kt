package com.example.skillsinema.domain


import com.example.skillsinema.data.Repository
import com.example.skillsinema.entity.Model

import javax.inject.Inject

class GetPremiereUseCase @Inject constructor(private val repository: Repository) {
        suspend fun executeGetPremiere(): Model {
        return repository.getPremiere()
    }
}