package com.example.skillsinema.domain


import com.example.skillsinema.DataRepository
import com.example.skillsinema.MyComponentManager
import com.example.skillsinema.MyEntryPoint
import com.example.skillsinema.entity.ModelGalerie
import com.example.skillsinema.repository.GalerieRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dagger.hilt.EntryPoints
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.scopes.ServiceScoped
import javax.inject.Inject

class GalerieUseCase @Inject constructor(
    val repository: GalerieRepository,
   var myComponentManager: MyComponentManager
    ) {
/*    @Inject
    lateinit var myComponentManager: MyComponentManager*/
    val myComponent = myComponentManager.get()
    val rep = EntryPoints.get(myComponent, MyEntryPoint::class.java).getDataRepository()
    suspend fun getGalerie(): List<ModelGalerie.Item>? {

        val id = rep.getValue()
        return repository.getGalerie(id)
    }
}