package com.example.skillsinema.ui.main.galerie

import android.app.ActionBar
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.skillsinema.R
import com.example.skillsinema.databinding.FragmentGalerieBinding
import com.example.skillsinema.entity.Film
import com.example.skillsinema.entity.ModelGalerie
import com.example.skillsinema.ui.main.home.AdapterBestFilm
import com.example.skillsinema.ui.main.home.TypeItem
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class GalerieFragment : Fragment() {

    private var _binding: FragmentGalerieBinding? = null
    private val binding get() = _binding!!

    val adapter = FullGalerieAdapter(
        onClick = { item -> onItemDetailClick(item) },

    )

    val bundle = Bundle()

    /*  companion object {
          fun newInstance() = GalerieFragment()
      }*/


    private val viewModel: GalerieViewModel by viewModels()




  /*  override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu., menu)
        menu.getItem(1).title ="1111"
    }*/

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGalerieBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)





        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



            viewModel.pagedFullGalerie.onEach {
                binding.galerieRecyclerview.adapter = adapter
                adapter.submitData(it)
            }.launchIn(viewLifecycleOwner.lifecycleScope)


        binding.chip1.setOnClickListener() {
            viewModel.pagedFullGalerie.onEach {
                binding.galerieRecyclerview.adapter = adapter
                adapter.submitData(it)
            }.launchIn(viewLifecycleOwner.lifecycleScope)
        }

        binding.chip2 .setOnClickListener{
            viewModel.pagesShootingGalerie.onEach {
                binding.galerieRecyclerview.adapter=adapter
                adapter.submitData(it)
            }.launchIn(viewLifecycleOwner.lifecycleScope)
        }

        binding.chip3 .setOnClickListener{
            viewModel.pagesWallpaperGalerie.onEach {
                binding.galerieRecyclerview.adapter=adapter
                adapter.submitData(it)
            }.launchIn(viewLifecycleOwner.lifecycleScope)
        }

    }


    private fun onItemDetailClick(item: ModelGalerie.Item) {
        item?.let {
            val bundle = Bundle().apply {
                putString("ArgURL", it.imageUrl)
            }
            findNavController().navigate(R.id.action_galerieFragment_to_showImageFragment, bundle)
        }


    }

}