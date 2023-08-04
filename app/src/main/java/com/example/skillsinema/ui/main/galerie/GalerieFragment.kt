package com.example.skillsinema.ui.main.galerie

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.skillsinema.R
import com.example.skillsinema.databinding.FragmentGalerieBinding
import com.example.skillsinema.databinding.FragmentItemInfoBinding
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


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGalerieBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.button1.setOnClickListener {
            viewModel.pagedFullGalerie.onEach {
                binding.galerieRecyclerview.adapter = adapter
                adapter.submitData(it)
            }.launchIn(viewLifecycleOwner.lifecycleScope)
        }





    }

}