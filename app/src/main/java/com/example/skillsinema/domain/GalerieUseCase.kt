package com.example.skillsinema.domain


import com.example.skillsinema.DataRepository
import com.example.skillsinema.entity.ModelGalerie
import com.example.skillsinema.repository.GalerieRepository
import javax.inject.Inject

class GalerieUseCase @Inject constructor(
    private val repository: GalerieRepository,
    private val dataRepository: DataRepository,
) {
    suspend fun getGalerie(page:Int): List<ModelGalerie.Item>? {

        val id = dataRepository.id
        return repository.getGalerie(page, id )
    }
}