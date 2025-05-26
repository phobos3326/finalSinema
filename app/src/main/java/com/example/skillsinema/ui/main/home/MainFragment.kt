package com.example.skillsinema.ui.main.home

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.skillsinema.DataRepository
import com.example.skillsinema.R
import com.example.skillsinema.databinding.FragmentMainBinding
import com.example.skillsinema.entity.Film
import com.example.skillsinema.entity.Model
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class MainFragment @Inject constructor() : Fragment() {

    @Inject
    lateinit var dataRepository: DataRepository



    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!



    private val adapter = AdapterBestFilm(

        onClick = { item , typeItem-> onItemDetailClick(item, typeItem) },

        onClickShowAll = { type, rvType -> onClickShowAll(type, rvType) }

    )


    private val adapterBestFilms = AdapterBestFilm(

        onClick = { item , typeItem-> onItemDetailClick(item, typeItem) },

        onClickShowAll = { type, rvType -> onClickShowAll(type, rvType) }

    )

    private val adapterFilteredFilms = AdapterFilteredFilms(
        onClick = { item , typeItem-> onItemDetailClick(item, typeItem) },

        onClickShowAll = { type, rvType -> onClickShowAll(type, rvType) }
    )

    private val adapterSerials = AdapterFilteredFilms(
        onClick = { item , typeItem-> onItemDetailClick(item, typeItem) },

        onClickShowAll = { type, rvType -> onClickShowAll(type, rvType) }
    )


    private val mainViewModel: MainViewModel by viewModels()

    val bundle = Bundle()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {



        Log.d(TAG, "onCreateView")
        _binding = FragmentMainBinding.inflate(inflater, container, false)

        binding.SHOWALL.setOnClickListener { View ->
            //onClickShowAll()
        }

        (activity as? AppCompatActivity)?.supportActionBar?.title = "Skillcinema"


        //mainViewModel.getFilters()
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        (activity as? AppCompatActivity)?.supportActionBar?.title = "Skillcinema"


        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            mainViewModel.modelPremiere.collect {
                binding.viewPager.adapter = adapter
                adapter.rvType = RVDataType.PREMIERES
                adapter.submitList(it)
            }
        }

        mainViewModel.topFilmModel.onEach {
            binding.TopFilmsRecyclerView.adapter = adapterBestFilms

            adapterBestFilms.submitList(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)




        viewLifecycleOwner.lifecycleScope.launch {
            mainViewModel.getFilters().onEach {
                binding.FilterFilmsRecyclerView.adapter = adapterFilteredFilms
                binding.filtered1.text = dataRepository.countryLabel
                adapterFilteredFilms.submitData(it)
                Log.d("PDATA", "$it")

            }.launchIn(viewLifecycleOwner.lifecycleScope)
        }


        viewLifecycleOwner.lifecycleScope.launch {
            mainViewModel.serials.onEach {
                binding.serialsRecyclerView.adapter = adapterSerials
                adapterSerials.rvType = RVDataType.SERIALS
                adapterSerials.submitData(it)
                Log.d("PDATA", "$it")

            }.launchIn(viewLifecycleOwner.lifecycleScope)
        }


        Log.d(TAG, "onViewCreated");



    }


    private fun onClickShowAll(type: TypeOfAdapter, rvType: RVDataType) {


        when (type) {
            TypeOfAdapter.WITHOUTPAGING -> {
                bundle.putSerializable("Arg2", TypeOfAdapter.WITHOUTPAGING)
            }

            TypeOfAdapter.WITHPAGING -> {
                bundle.putSerializable("Arg2", TypeOfAdapter.WITHPAGING)
            }
        }

        when (rvType) {
            RVDataType.TOP250 -> {
                bundle.putSerializable("Arg3", RVDataType.TOP250)
            }

            RVDataType.COUNTRYWITHGENRE -> {
                bundle.putSerializable("Arg3", RVDataType.COUNTRYWITHGENRE)
            }

            RVDataType.PREMIERES -> {
                bundle.putSerializable("Arg3", RVDataType.PREMIERES)
            }

            RVDataType.SERIALS -> {
                bundle.putSerializable("Arg3", RVDataType.SERIALS)
            }

            RVDataType.COLLECTION -> {
                bundle.putSerializable("Arg3", RVDataType.COLLECTION)
            }

        }

        findNavController().navigate(R.id.action_home_fragment_to_showAllFragment, bundle)
    }


    /* private fun onItemClick(item: Model.Item) {

         bundle.putInt("Arg", item.kinopoiskId)
         findNavController().navigate(R.id.action_mainFragment_to_itemInfoFragment, bundle)
         item.kinopoiskId?.let { mainViewModel.insertItem(it) }
     }*/

    private fun onItemDetailClick(item: Film, type:TypeItem) {
        if (item.kinopoiskId == null) {
            item.filmId?.let { bundle.putInt("Arg", it) }
            item.filmId?.let { mainViewModel.insertItem(it) }
            item.filmId?.let { mainViewModel.isertItemToDb(type, it) }
        } else {
            item.kinopoiskId.let { bundle.putInt("Arg", it) }
            item.kinopoiskId?.let { mainViewModel.isertItemToDb(type, it) }

        }
        findNavController().navigate(R.id.action_mainFragment_to_itemInfoFragment, bundle)


    }


    fun ContentFragment() {
        Log.d(TAG, "Constructor")
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d(TAG, "onAttach")
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        Log.d(TAG, "onViewStateRestored")
    }

    override fun onStart() {
        super.onStart()
        (activity as? AppCompatActivity)?.supportActionBar?.title = "Skillcinema"
        Log.d(TAG, "onStart")
    }

    override fun onResume() {
        super.onResume()

        Log.d(TAG, "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG, "onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d(TAG, "onDetach")
    }

    private val TAG = "ContentFragment"
    private val TAG2 = "TypeAdapter"

}