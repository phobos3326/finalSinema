package com.example.skillsinema.ui.main.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.skillsinema.R
import com.example.skillsinema.databinding.FragmentItemInfoBinding
import com.example.skillsinema.databinding.FragmentThirdBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


@AndroidEntryPoint
class ThirdFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    private var _binding: FragmentThirdBinding? = null
    private val binding get() = _binding!!

    val viewModel: ThirdFragmentViewModel by viewModels()

    val adapter = CollectionAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        _binding = FragmentThirdBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.createCollectionTextView.setOnClickListener {
            viewModel.wantToSee()
        }

        viewModel.collection.onEach {
            binding.collectionRecycler.adapter = adapter
            adapter.submitList(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)


    }


}