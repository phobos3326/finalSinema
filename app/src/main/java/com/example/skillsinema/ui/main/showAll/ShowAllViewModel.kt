package com.example.skillsinema.ui.main.showAll

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.paging.*
import com.example.skillsinema.R
import com.example.skillsinema.entity.Film
import com.example.skillsinema.datasource.FilmPagingSourse
import com.example.skillsinema.ui.main.ItemInfo.StateItemFilmInfo
import com.example.skillsinema.ui.main.home.TypeOfAdapter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ShowAllViewModel @Inject constructor(
    private var pagingSource: FilmPagingSourse
) : ViewModel() {

    private val _state = MutableStateFlow<TypeOfAdapter>(TypeOfAdapter.WITHOUTPAGING)
    val state = _state.asStateFlow()

    val pagedFilms: Flow<PagingData<Film>> = Pager(
        config = PagingConfig(
            pageSize = 20,
            enablePlaceholders = true

        ),
        pagingSourceFactory = { pagingSource }
    ).flow.cachedIn(viewModelScope)




/*    private fun onClickShowAll(type:TypeOfAdapter) {


        when(type){
            TypeOfAdapter.WITHOUTPAGING -> {
                bundle.putSerializable("Arg2", TypeOfAdapter.WITHOUTPAGING)
            }
            TypeOfAdapter.WITHPAGING->{
                bundle.putSerializable("Arg2", TypeOfAdapter.WITHPAGING)
            }
        }





        findNavController().navigate(R.id.action_home_fragment_to_showAllFragment, bundle)
    }
    */


}