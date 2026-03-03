package com.example.skillsinema.presentation.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.skillsinema.databinding.FragmentMainBinding
import com.example.skillsinema.presentation.base.NavigationHelper
import com.example.skillsinema.presentation.base.collectWithLifecycle
import com.example.skillsinema.presentation.ui.adapters.FilmAdapter
import com.example.skillsinema.presentation.ui.adapters.FilmListItem
import dagger.hilt.android.AndroidEntryPoint

/**
 * Главная страница (премьеры, топ, сериалы, рекомендованное).
 */
@AndroidEntryPoint
class MainFragment : Fragment() {

    private val viewModel: MainViewModel by viewModels()

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var premiereAdapter: FilmAdapter
    private lateinit var topFilmsAdapter: FilmAdapter
    private lateinit var serialsAdapter: FilmAdapter
    private lateinit var filteredAdapter: FilmAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapters()
        observeState()
    }

    private fun setupAdapters() {
        premiereAdapter = FilmAdapter(
            onFilmClick = { filmId -> NavigationHelper.navigateToFilmDetails(this, filmId) },
            onShowAllClick = { category -> NavigationHelper.navigateToShowAll(this, category) }
        )
        binding.rvPremieres.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = premiereAdapter
        }

        topFilmsAdapter = FilmAdapter(
            onFilmClick = { filmId -> NavigationHelper.navigateToFilmDetails(this, filmId) },
            onShowAllClick = { category -> NavigationHelper.navigateToShowAll(this, category) }
        )
        binding.rvTopFilms.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = topFilmsAdapter
        }

        serialsAdapter = FilmAdapter(
            onFilmClick = { filmId -> NavigationHelper.navigateToFilmDetails(this, filmId) },
            onShowAllClick = { category -> NavigationHelper.navigateToShowAll(this, category) }
        )
        binding.rvSerials.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = serialsAdapter
        }

        filteredAdapter = FilmAdapter(
            onFilmClick = { filmId -> NavigationHelper.navigateToFilmDetails(this, filmId) },
            onShowAllClick = { category -> NavigationHelper.navigateToShowAll(this, category) }
        )
        binding.rvFiltered.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = filteredAdapter
        }
    }

    private fun renderState(state: MainUiState) {
        android.util.Log.d("MainFragment", "renderState: isLoading=${state.isLoading}, error=${state.error}, premiereItems=${state.premiereItems.size}")
        binding.progressLoading.visibility = if (state.isLoading) View.VISIBLE else View.GONE

        updateRecyclerView(
            binding.tvPremieresLabel,
            binding.rvPremieres,
            state.premiereItems,
            premiereAdapter
        )

        updateRecyclerView(
            binding.tvTopFilmsLabel,
            binding.rvTopFilms,
            state.topFilmItems,
            topFilmsAdapter
        )

        updateRecyclerView(
            binding.tvSerialsLabel,
            binding.rvSerials,
            state.serialItems,
            serialsAdapter
        )

        updateRecyclerView(
            binding.tvFilteredLabel,
            binding.rvFiltered,
            state.filteredItems,
            filteredAdapter
        )

       /* state.error?.let { error ->
            android.util.Log.e("MainFragment", "Error: $error")
            showError(error)
        }*/
    }

    private fun updateRecyclerView(
        label: android.widget.TextView,
        recyclerView: androidx.recyclerview.widget.RecyclerView,
        items: List<FilmListItem>,
        adapter: FilmAdapter
    ) {
        android.util.Log.d("MainFragment", "updateRecyclerView: items.size=${items.size}")
        if (items.isNotEmpty()) {
            label.visibility = android.view.View.VISIBLE
            recyclerView.visibility = android.view.View.VISIBLE
            adapter.submitList(items)
            android.util.Log.d("MainFragment", "submitList called with ${items.size} items")
        } else {
            label.visibility = android.view.View.GONE
            recyclerView.visibility = android.view.View.GONE
        }
    }

    private fun observeState() {
        viewModel.state.collectWithLifecycle(this) { state ->
            renderState(state)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
