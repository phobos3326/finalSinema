package com.example.skillsinema.ui.main.home

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels

import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.skillsinema.R

import com.example.skillsinema.databinding.FragmentMainBinding
import com.example.skillsinema.entity.Model
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment @Inject constructor() : Fragment() {


    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private val adapter = MyAdapter { onItemClick(it) }

    //private val adapterBestFilms = AdapterBestFilm()

    private val pagedAdapter = PagedAdapterBestFilm()

    val scope = CoroutineScope(Dispatchers.Default)
    val scope2 = CoroutineScope(Dispatchers.Default)

    private val mainViewModel: MainViewModel by viewModels()
    // val mainViewModel: MainViewModel by hiltNavGraphViewModels(R.id.nav_graph)

    val bundle = Bundle()

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


        // obtainNavHostFragment(parentFragmentManager,"home_fragment", R.navigation.home_nav_graph,)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            mainViewModel.modelPremiere.collect {
                binding.viewPager.adapter = adapter
                adapter.addToList(it)
               // Log.d("TAG", "${it.size}")

            }


        }
      /*  viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            mainViewModel.topFilmModel.collect {
                *//*binding.TopFilmsRecyclerView.adapter = adapterBestFilms
                adapterBestFilms.addToList(it)*//*



            }
        }*/




        viewModel.pagedFilms.onEach {
            binding.TopFilmsRecyclerView.adapter=pagedAdapter
            pagedAdapter.submitData(it)
            //pagedAdapter.addToList(it)
            Log.d("TAG", "${it}")
        }.launchIn(viewLifecycleOwner.lifecycleScope)

    }


    private fun onItemClick(item: Model.Item) {
        bundle.putInt("Arg", item.kinopoiskId)
        findNavController().navigate(R.id.action_mainFragment_to_itemInfoFragment, bundle)

    }

    private fun obtainNavHostFragment(
        fragmentManager: FragmentManager,
        fragmentTag: String,
        navGraphId: Int,
        containerId: Int
    ): NavHostFragment {
        // If the Nav Host fragment exists, return it
        val existingFragment = fragmentManager.findFragmentByTag(fragmentTag) as NavHostFragment?
        existingFragment?.let { return it }

        // Otherwise, create it and return it.
        val navHostFragment = NavHostFragment.create(navGraphId)
        fragmentManager.beginTransaction()
            .add(containerId, navHostFragment, fragmentTag)
            .commitNow()
        return navHostFragment
    }

}