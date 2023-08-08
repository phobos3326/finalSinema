package com.example.skillsinema.ui.main.galerie

import android.app.ActionBar
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.skillsinema.R
import com.example.skillsinema.databinding.FragmentGalerieBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class GalerieFragment : Fragment() {

    private var _binding: FragmentGalerieBinding? = null
    private val binding get() = _binding!!

    val adapter = FullGalerieAdapter()

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


      //  actionBar.hide() // or even hide the actionbar


        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        //val title: String = actionBar?.getTitle().toString() // get the title

        binding.button1.setOnClickListener {
            viewModel.pagedFullGalerie.onEach {
                binding.galerieRecyclerview.adapter = adapter
                adapter.submitData(it)
            }.launchIn(viewLifecycleOwner.lifecycleScope)
        }

        binding.button2.setOnClickListener{
            viewModel.pagesShootingGalerie.onEach {
                binding.galerieRecyclerview.adapter=adapter
                adapter.submitData(it)
            }.launchIn(viewLifecycleOwner.lifecycleScope)
        }

        binding.button3.setOnClickListener{
            viewModel.pagesWallpaperGalerie.onEach {
                binding.galerieRecyclerview.adapter=adapter
                adapter.submitData(it)
            }.launchIn(viewLifecycleOwner.lifecycleScope)
        }




    }

}