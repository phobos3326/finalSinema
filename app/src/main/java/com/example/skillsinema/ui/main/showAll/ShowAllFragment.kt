package com.example.skillsinema.ui.main.showAll


import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.skillsinema.R
import com.example.skillsinema.entity.Film

import com.example.skillsinema.databinding.FragmentShowAllBinding
import com.example.skillsinema.entity.Model
import com.example.skillsinema.ui.main.home.AdapterBestFilm
import com.example.skillsinema.ui.main.home.AdapterFilteredFilms
import com.example.skillsinema.ui.main.home.RVDataType
import com.example.skillsinema.ui.main.home.TypeItem
import com.example.skillsinema.ui.main.home.TypeOfAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch


@AndroidEntryPoint
class ShowAllFragment : Fragment() {


    private var _binding: FragmentShowAllBinding? = null
    private val binding get() = _binding!!
    var collectionName=""

    val bundle = Bundle()

    private val adapterPagedFilm = AdapterPagedFilm (
        onClick = { item , typeItem-> onItemDetailClick(item, typeItem) }
    )
    private val adapterBestFilms = AdapterBestFilm(

        onClick = { item , typeItem-> onItemDetailClick(item, typeItem) },

        onClickShowAll = { type, rvType -> onClickShowAll(type, rvType) }

    )

    companion object {
        //fun newInstance() = ShowAllFragment()
    }

    private val viewModel: ShowAllViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentShowAllBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            val arg2 = it.getSerializable("Arg2") as? TypeOfAdapter
            arg2?.let { it1 -> viewModel.setState(it1) }
            val arg3 = it.getSerializable("Arg3") as RVDataType?
            viewModel.setStateType(arg3)

             collectionName = it.getString("CollectionName").toString()
            viewModel.showCollection(collectionName)
        }

        /* viewModel.adapterType = arguments?.getSerializable("Arg2") as TypeOfAdapter?
         viewModel.RVDataType = arguments?.getSerializable("Arg3") as RVDataType?*/


        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect {
                    when (it) {
                        TypeOfAdapter.WITHPAGING -> {
                            binding.SHOWALLRecyclerView.adapter = adapterPagedFilm
                        }

                        TypeOfAdapter.WITHOUTPAGING -> {
                            binding.SHOWALLRecyclerView.adapter = adapterBestFilms
                        }
                    }
                }
            }
        }


        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.stateRVDataType.collect {
                    when (it) {
                        RVDataType.TOP250 -> {
                            binding.textView.text = "ТОП 250"
                            viewModel.pagedFilms.onEach {
                                binding.SHOWALLRecyclerView.adapter = adapterPagedFilm
                                //dapterBestFilms.loading = false

                                adapterPagedFilm.submitData(it)
                                //adapterBestFilms.loading =true

                            }.launchIn(viewLifecycleOwner.lifecycleScope)
                        }

                        RVDataType.PREMIERES -> {

                            viewModel.modelPremiere.onEach {
                                binding.SHOWALLRecyclerView.adapter = adapterBestFilms
                                adapterBestFilms.submitList(it)
                            }.launchIn(viewLifecycleOwner.lifecycleScope)

                        }

                        RVDataType.SERIALS -> {

                            binding.textView.text = "сериалы"
                            viewLifecycleOwner.lifecycleScope.launch {
                                viewModel.serials.onEach {
                                    binding.SHOWALLRecyclerView.adapter = adapterPagedFilm
                                    adapterPagedFilm.submitData(it)
                                }.launchIn(viewLifecycleOwner.lifecycleScope)
                            }

                        }

                        RVDataType.COUNTRYWITHGENRE -> {
                            binding.textView.text = "страна и жанр"
                            viewLifecycleOwner.lifecycleScope.launch {
                                viewModel.getFilters().onEach {
                                    binding.SHOWALLRecyclerView.adapter = adapterPagedFilm
                                    adapterPagedFilm.submitData(it)
                                }.launchIn(viewLifecycleOwner.lifecycleScope)
                            }

                        }

                        RVDataType.COLLECTION -> {
                            binding.textView.text = collectionName
                            viewLifecycleOwner.lifecycleScope.launch {
                                viewModel.collection.onEach {
                                    binding.SHOWALLRecyclerView.adapter = adapterBestFilms
                                    adapterBestFilms.submitList(it)
                                }.launchIn(viewLifecycleOwner.lifecycleScope)
                            }
                        }
                    }
                }
            }
        }


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

       /* when (rvType) {
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
        }*/

        findNavController().navigate(R.id.action_home_fragment_to_showAllFragment, bundle)
    }


    private fun onItemDetailClick(item: Film, type: TypeItem) {
        if (item.kinopoiskId == null) {
            item.filmId?.let { bundle.putInt("Arg", it) }
            item.filmId?.let { viewModel.insertItem(it) }
            item.filmId?.let { viewModel.isertItemToDb(type, it) }
        } else {
            item.kinopoiskId.let { bundle.putInt("Arg", it) }
            item.kinopoiskId?.let { viewModel.isertItemToDb(type, it) }

        }
        findNavController().navigate(R.id.action_showAllFragment_to_itemInfoFragment, bundle)


    }




    private fun onItemClick(item: Film) {
        item.filmId?.let { bundle.putInt("Arg", it) }
        findNavController().navigate(R.id.action_showAllFragment_to_itemInfoFragment, bundle)

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

    private val TAG = "ShowAllFragment"



}