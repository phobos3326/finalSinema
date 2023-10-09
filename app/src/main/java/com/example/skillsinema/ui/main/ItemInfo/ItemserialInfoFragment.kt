package com.example.skillsinema.ui.main.ItemInfo

import android.os.Bundle
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
import com.example.skillsinema.databinding.FragmentItemserialInfoFragmentBinding

import com.example.skillsinema.entity.Model
import com.example.skillsinema.entity.ModelSeasons

import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ItemserialInfoFragment : Fragment() {


    val bundle = Bundle()
    private var _binding: FragmentItemserialInfoFragmentBinding? = null
    private val binding get() = _binding!!
    private val adapterEpisodes = AdapterEpisodes()
    private val adapterSeasons = AdapterSeasons {
        onItemClick(it)
    }

    private val mainViewModel: SerialSeasonsViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentItemserialInfoFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        mainViewModel.episodes.onEach {
            binding.listEpisodesItemsRecyclerView.adapter = adapterEpisodes
            adapterEpisodes.submitList(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)



        mainViewModel.seasons.onEach {
            binding.listSeasonsRecyclerView.adapter = adapterSeasons
            adapterSeasons.submitList(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)



    }


    private fun onItemClick(item: ModelSeasons.Item) {

        mainViewModel.setSeasonNumber(item.number)
    }


}