package com.example.skillsinema.ui.main.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.skillsinema.R
import com.example.skillsinema.adapter.Film
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

    private val adapterBestFilms = AdapterBestFilm {
        onItemDetailClick(it)
    }

    private val adapterFilteredFilms =AdapterFilteredFilms()

    // private val pagedAdapter = PagedAdapterBestFilm { onItemClick(it) }

    val scope = CoroutineScope(Dispatchers.Default)
    val scope2 = CoroutineScope(Dispatchers.Default)

    private val mainViewModel: MainViewModel by viewModels()
    // val mainViewModel: MainViewModel by hiltNavGraphViewModels(R.id.nav_graph)

    val bundle = Bundle()

    companion object {
        // fun newInstance() = MainFragment()
    }

    //private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
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

        binding.SHOWALL.setOnClickListener { View ->
            onClickShowAll()
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            mainViewModel.modelPremiere.collect {

                binding.viewPager.adapter = adapter

                adapter.addToList(it)
                // Log.d("TAG", "${it.size}")


            }


        }


        mainViewModel.topFilmModel.onEach {
            binding.TopFilmsRecyclerView.adapter = adapterBestFilms
            //dapterBestFilms.loading = false
            binding.TopFilmsRecyclerView.scrollToPosition(1)
            adapterBestFilms.submitList(it)
            //adapterBestFilms.loading =true

        }.launchIn(viewLifecycleOwner.lifecycleScope)



        mainViewModel.pagedFilteredFilms.onEach {

            binding.FilterFilmsRecyclerView.adapter =adapterFilteredFilms
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
        bundle.putInt("Arg", item.filmId)
        findNavController().navigate(R.id.action_mainFragment_to_itemInfoFragment, bundle)
    }


}