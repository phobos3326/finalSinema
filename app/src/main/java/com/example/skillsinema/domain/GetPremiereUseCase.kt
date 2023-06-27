package com.example.skillsinema.domain


import com.example.skillsinema.repository.Repository
import com.example.skillsinema.entity.Model

import javax.inject.Inject

class GetPremiereUseCase @Inject constructor(private val repository: Repository) {
        suspend fun executeGetPremiere(year: Int, month: String): Model {
        return repository.getPremiere(year, month )
    }
}