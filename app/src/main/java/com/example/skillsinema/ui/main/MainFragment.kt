package com.example.skillsinema.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.skillsinema.R
import com.example.skillsinema.adapter.Adapter
import com.example.skillsinema.adapter.MyAdapter
import com.example.skillsinema.adapter.MyViewType
import com.example.skillsinema.data.Repository

import com.example.skillsinema.databinding.FragmentMainBinding
import com.example.skillsinema.entity.Model
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment @Inject constructor() : Fragment() {



    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private val adapter = MyAdapter {onItemClick(it)}
    val scope = CoroutineScope(Dispatchers.Default)

    private val mainViewModel: MainViewModel by viewModels()

    val bundle =Bundle()

    companion object {
       // fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        // TODO: Use the ViewModel


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            mainViewModel.modelPremiere.collect {
                binding.viewPager.adapter = adapter
                adapter.addToList(it)
                Log.d("TAG", "${it.size}")

            }

        }
    }


    private fun onItemClick(item: MyViewType) {
        bundle.putInt("Arg", item.kinopoiskId)
        findNavController().navigate(R.id.action_mainFragment_to_itemInfoFragment, bundle)

    }

}