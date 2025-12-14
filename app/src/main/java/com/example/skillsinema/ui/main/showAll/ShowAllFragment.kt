package com.example.skillsinema.ui.main.showAll

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.skillsinema.R
import com.example.skillsinema.databinding.FragmentShowAllBinding

import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ShowAllFragment : Fragment() {

    private var _binding: FragmentShowAllBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ShowAllViewModel by viewModels()
    private lateinit var showAllAdapter: ShowAllAdapter

    private val category: String by lazy {
        arguments?.getString("category") ?: "premieres"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShowAllBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // ❌ Не настраиваем Toolbar
        setupAdapter()
       // setupSwipeRefresh()
        observeState()

        viewModel.loadFilms(category)
    }

    private fun setupAdapter() {
        showAllAdapter = ShowAllAdapter { filmId ->
            navigateToFilmDetails(filmId)
        }

        binding.SHOWALLRecyclerView.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = showAllAdapter
        }
    }

   /* private fun setupSwipeRefresh() {
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.loadFilms(category)
        }
    }*/

    private fun observeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collect { state ->
                renderState(state)
            }
        }
    }

    private fun renderState(state: ShowAllUiState) {
       /* binding.swipeRefresh.isRefreshing = false
        binding.progressLoading.visibility = if (state.isLoading) View.VISIBLE else View.GONE*/

        if (state.films.isNotEmpty()) {
            binding.SHOWALLRecyclerView.visibility = View.VISIBLE
           // binding.tvEmptyState.visibility = View.GONE
            showAllAdapter.submitList(state.films)
        } else {
            binding.SHOWALLRecyclerView.visibility = if (state.isLoading) View.GONE else View.VISIBLE
            //binding.tvEmptyState.visibility = if (state.isLoading) View.GONE else View.VISIBLE
        }

        state.error?.let { error ->
            showError(error)
        }
    }

    private fun navigateToFilmDetails(filmId: Int) {
        try {
            val bundle = Bundle().apply {
                putInt("film_id", filmId)
            }
            findNavController().navigate(
                R.id.action_showAllFragment_to_itemInfoFragment,
                bundle
            )
        } catch (e: Exception) {
            showError(e.message ?: "Ошибка навигации")
        }
    }

    private fun showError(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG)
            .setAction("OK") { }
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
