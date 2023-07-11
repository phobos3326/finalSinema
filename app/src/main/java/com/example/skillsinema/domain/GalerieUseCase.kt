package com.example.skillsinema.domain



import com.example.skillsinema.entity.ModelGalerie
import com.example.skillsinema.repository.GalerieRepository
import javax.inject.Inject

class GalerieUseCase @Inject constructor ( val repository: GalerieRepository) {

    suspend fun getGalerie(): List<ModelGalerie.Item>? {
       return repository.getGalerie()
    }
}