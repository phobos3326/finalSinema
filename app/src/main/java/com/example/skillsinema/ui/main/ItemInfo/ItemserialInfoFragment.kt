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

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ItemserialInfoFtagment.newInstance] factory method to
 * create an instance of this fragment.
 */

@AndroidEntryPoint
class ItemserialInfoFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    val bundle = Bundle()
    private var _binding: FragmentItemserialInfoFragmentBinding? = null
    private val binding get() = _binding!!
    private val adapterEpisodes = AdapterEpisodes()
    private val adapterSeasons = AdapterSeasons {
        onItemClick(it)
    }

    private val mainViewModel: SerialSeasonsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

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

    /* companion object {
        */
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ItemserialInfoFtagment.
     *//*
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ItemserialInfoFtagment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }*/
}