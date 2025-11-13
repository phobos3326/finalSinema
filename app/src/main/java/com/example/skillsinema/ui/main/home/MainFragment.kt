package com.example.skillsinema.presentation.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.skillsinema.R

import com.example.skillsinema.databinding.FragmentMainBinding
import com.example.skillsinema.presentation.ui.adapters.FilmAdapter
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : Fragment() {

    // ViewBinding - nullable для lifecycle safety
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by viewModels()

    // Многотипные адаптеры для каждой секции
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
        setupClickListeners()
    }

    private fun setupAdapters() {
        // ← Адаптеры с двумя callback (фильм + показать все)
        premiereAdapter = FilmAdapter(
            onFilmClick = { filmId -> navigateToFilmDetails(filmId) },
            onShowAllClick = { category -> navigateToShowAll(category) }
        )

        topFilmsAdapter = FilmAdapter(
            onFilmClick = { filmId -> navigateToFilmDetails(filmId) },
            onShowAllClick = { category -> navigateToShowAll(category) }
        )

        serialsAdapter = FilmAdapter(
            onFilmClick = { filmId -> navigateToFilmDetails(filmId) },
            onShowAllClick = { category -> navigateToShowAll(category) }
        )

        filteredAdapter = FilmAdapter(
            onFilmClick = { filmId -> navigateToFilmDetails(filmId) },
            onShowAllClick = { category -> navigateToShowAll(category) }
        )

        // Настройка RecyclerView
        binding.viewPager .apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = premiereAdapter
        }

        binding.TopFilmsRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = topFilmsAdapter
        }

        binding.FilterFilmsRecyclerView .apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = serialsAdapter
        }

        binding.serialsRecyclerView .apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = filteredAdapter
        }
    }

    private fun observeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            // ← Простое наблюдение за состоянием без Flow
            while (true) {
                renderState(viewModel.state)
                kotlinx.coroutines.delay(100) // Проверяем каждые 100мс
            }
        }
    }

    private fun renderState(state: MainUiState) {
        // Управление ProgressBar
       // binding.progressLoading.visibility = if (state.isLoading) View.VISIBLE else View.GONE

        // ← ПРОСТОЕ ПРИСВОЕНИЕ готовых List<FilmListItem> из ViewModel:
        premiereAdapter.submitList(state.premiereItems)
        topFilmsAdapter.submitList(state.topFilmItems)
        serialsAdapter.submitList(state.serialItems)
        filteredAdapter.submitList(state.filteredItems)

        // Показ ошибок
        state.error?.let { error ->
            showError(error)
        }

        // Управление видимостью секций
       // updateSectionVisibility(state)
    }

    /*private fun updateSectionVisibility(state: MainUiState) {
        // Премьеры
        binding.tvPremieresLabel.visibility =
            if (state.premiereItems.isNotEmpty()) View.VISIBLE else View.GONE
        binding.rvPremieres.visibility =
            if (state.premiereItems.isNotEmpty()) View.VISIBLE else View.GONE

        // Топ фильмы
        binding.tvTopFilmsLabel.visibility =
            if (state.topFilmItems.isNotEmpty()) View.VISIBLE else View.GONE
        binding.rvTopFilms.visibility =
            if (state.topFilmItems.isNotEmpty()) View.VISIBLE else View.GONE

        // Сериалы
        binding.tvSerialsLabel.visibility =
            if (state.serialItems.isNotEmpty()) View.VISIBLE else View.GONE
        binding.rvSerials.visibility =
            if (state.serialItems.isNotEmpty()) View.VISIBLE else View.GONE

        // Подборка по фильтрам
        binding.tvFilteredLabel.visibility =
            if (state.filteredItems.isNotEmpty()) View.VISIBLE else View.GONE
        binding.rvFilteredFilms.visibility =
            if (state.filteredItems.isNotEmpty()) View.VISIBLE else View.GONE
    }*/

    private fun navigateToFilmDetails(filmId: Int) {
        try {
            val bundle = Bundle().apply {
                putInt("film_id", filmId)
            }

            findNavController().navigate(
                R.id.action_mainFragment_to_itemInfoFragment,
                bundle
            )
        } catch (e: Exception) {
            showError("Ошибка навигации: ${e.message}")
        }
    }

    private fun showError(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG)
            .setAction("OK") { /* dismiss */ }
            .show()
    }

    private fun navigateToShowAll(category: String) {
        try {
            val bundle = Bundle().apply {
                putString("category", category)
            }
            findNavController().navigate(
                R.id.action_home_fragment_to_showAllFragment,
                bundle
            )
        } catch (e: Exception) {
            showError("Ошибка перехода к списку: ${e.message}")
        }
    }

    private fun setupClickListeners() {
        // Клики по заголовкам дублируют функциональность кнопок "Показать все"
        binding.viewPager .setOnClickListener {
            navigateToShowAll("premieres")
        }

        binding.TopFilmsRecyclerView .setOnClickListener {
            navigateToShowAll("top_films")
        }

        binding.FilterFilmsRecyclerView .setOnClickListener {
            navigateToShowAll("serials")
        }

        binding.serialsRecyclerView .setOnClickListener {
            navigateToShowAll("filtered")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
