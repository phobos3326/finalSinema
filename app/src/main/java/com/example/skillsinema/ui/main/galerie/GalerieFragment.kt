package com.example.skillsinema.ui.main.galerie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.skillsinema.databinding.FragmentGalerieBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class GalerieFragment : Fragment() {

    private var _binding: FragmentGalerieBinding? = null
    private val binding get() = _binding!!

    private val viewModel: GalerieViewModel by viewModels()

    private val adapter = FullGalerieAdapter { imageUrl ->
        onImageClick(imageUrl)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGalerieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeGallery()
    }

    private fun setupRecyclerView() {
        binding.galerieRecyclerview .apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = this@GalerieFragment.adapter
            setHasFixedSize(true)
        }
    }

    private fun observeGallery() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.pagedFullGalerie.collectLatest { pagingData ->
                adapter.submitData(pagingData)
            }
        }
    }

    private fun onImageClick(imageUrl: String) {
        val action = GalerieFragmentDirections
            .actionGalerieFragmentToShowImageFragment(imageUrl)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.galerieRecyclerview.adapter = null
        _binding = null
    }
}
