package com.example.skillsinema.ui.main.home

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController
import com.example.skillsinema.R
import com.example.skillsinema.domain.GetPremiereUseCase
import com.example.skillsinema.entity.Model
import dagger.Module
import dagger.Provides
import dagger.assisted.Assisted
import dagger.hilt.InstallIn

import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@HiltViewModel
class MainViewModel @Inject constructor(
   // @ApplicationContext private val context: ApplicationContext,
    private val data: GetPremiereUseCase,
    //private val navController: NavController
) : ViewModel() {
    private val _premiereModel = MutableStateFlow<List<Model.Item>>(emptyList())
    val modelPremiere = _premiereModel.asStateFlow()

    var bundle = Bundle()

    init {
        viewModelScope.launch {
            loadPremieres()
        }
    }


    private fun loadPremieres() {
        //navController.navigate(R.id.action_mainFragment_to_itemInfoFragment, bundle)
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                data.executeGetPremiere().items
            }.fold(
                onSuccess = {
                    _premiereModel.value = it
                    //Log.d("MainViewModel", (it ?: " load").toString())
                },
                onFailure = { Log.d("MainViewModel", it.message ?: "not load") }
            )
        }
    }
}


/*
@Module
@InstallIn(SingletonComponent::class)
object NavigationModule {

    @Provides
    @Singleton
    fun provideNavController(@ApplicationContext context: Context) = NavHostController(context).apply {
        navigatorProvider.addNavigator(ComposeNavigator())
        navigatorProvider.addNavigator(DialogNavigator())
    }
}*/
