package com.example.skillsinema.ui.main.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.skillsinema.R
import com.example.skillsinema.entity.Film
import com.example.skillsinema.databinding.FragmentMainBinding
import com.example.skillsinema.entity.Model
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment @Inject constructor() : Fragment() {






    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private val adapter = MyAdapter { onItemClick(it) }

    private val adapterBestFilms = AdapterBestFilm {
        onItemDetailClick(it)
    }

    private val adapterFilteredFilms = AdapterFilteredFilms {
        onItemDetailClick(it)
    }


    private val mainViewModel: MainViewModel by viewModels()

    val bundle = Bundle()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.SHOWALL.setOnClickListener { View ->
            onClickShowAll()
        }
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            mainViewModel.modelPremiere.collect {
                binding.viewPager.adapter = adapter
                adapter.addToList(it)
            }
        }

        mainViewModel.topFilmModel.onEach {
            binding.TopFilmsRecyclerView.adapter = adapterBestFilms

            adapterBestFilms.submitList(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        mainViewModel.pagedFilteredFilms.onEach {

            binding.FilterFilmsRecyclerView.adapter = adapterFilteredFilms
            adapterFilteredFilms.submitData(it)
            Log.d("PDATA", "$it")



        }.launchIn(viewLifecycleOwner.lifecycleScope)



    }


    private fun onClickShowAll() {
        findNavController().navigate(R.id.action_home_fragment_to_showAllFragment)
    }

    private fun onItemClick(item: Model.Item) {

        bundle.putInt("Arg", item.kinopoiskId)
        findNavController().navigate(R.id.action_mainFragment_to_itemInfoFragment, bundle)
    }

    private fun onItemDetailClick(item: Film) {
        if (item.kinopoiskId == null) {
            item.filmId?.let { bundle.putInt("Arg", it) }
        } else {
            item.kinopoiskId.let { bundle.putInt("Arg", it) }
        }
        findNavController().navigate(R.id.action_mainFragment_to_itemInfoFragment, bundle)
    }

}