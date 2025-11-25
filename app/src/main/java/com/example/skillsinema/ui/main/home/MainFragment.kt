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

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by viewModels()

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
        setupSwipeRefresh()
        observeState()
    }

    private fun setupAdapters() {
        // Адаптер для премьер
        premiereAdapter = FilmAdapter(
            onFilmClick = { filmId -> navigateToFilmDetails(filmId) },
            onShowAllClick = { category -> navigateToShowAll(category) }
        )
        binding.rvPremieres.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = premiereAdapter
        }

        // Адаптер для топ фильмов
        topFilmsAdapter = FilmAdapter(
            onFilmClick = { filmId -> navigateToFilmDetails(filmId) },
            onShowAllClick = { category -> navigateToShowAll(category) }
        )
        binding.rvTopFilms.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = topFilmsAdapter
        }

        // Адаптер для сериалов
        serialsAdapter = FilmAdapter(
            onFilmClick = { filmId -> navigateToFilmDetails(filmId) },
            onShowAllClick = { category -> navigateToShowAll(category) }
        )
        binding.rvSerials.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = serialsAdapter
        }

        // Адаптер для фильтрованных
        filteredAdapter = FilmAdapter(
            onFilmClick = { filmId -> navigateToFilmDetails(filmId) },
            onShowAllClick = { category -> navigateToShowAll(category) }
        )
        binding.rvFiltered.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = filteredAdapter
        }
    }

    private fun setupSwipeRefresh() {
        // Если у вас есть SwipeRefreshLayout
        // binding.swipeRefresh.setOnRefreshListener {
        //     viewModel.refresh()
        // }
    }

    // ✅ НАБЛЮДАЕМ ЗА StateFlow
    private fun observeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collect { state ->
                renderState(state)
            }
        }
    }

    // ✅ ПРОСТАЯ renderState - просто передаем готовые данные
    private fun renderState(state: MainUiState) {
        // Прогресс загрузки
        binding.progressLoading.visibility = if (state.isLoading) View.VISIBLE else View.GONE

        // Останавливаем SwipeRefresh если есть
        // binding.swipeRefresh.isRefreshing = false

        // Премьеры
        if (state.premiereItems.isNotEmpty()) {
            premiereAdapter.submitList(state.premiereItems)
            binding.tvPremieresLabel.visibility = View.VISIBLE
            binding.rvPremieres.visibility = View.VISIBLE
        } else {
            binding.tvPremieresLabel.visibility = View.GONE
            binding.rvPremieres.visibility = View.GONE
        }

        // Топ фильмы
        if (state.topFilmItems.isNotEmpty()) {
            topFilmsAdapter.submitList(state.topFilmItems)
            binding.tvTopFilmsLabel.visibility = View.VISIBLE
            binding.rvTopFilms.visibility = View.VISIBLE
        } else {
            binding.tvTopFilmsLabel.visibility = View.GONE
            binding.rvTopFilms.visibility = View.GONE
        }

        // Сериалы
        if (state.serialItems.isNotEmpty()) {
            serialsAdapter.submitList(state.serialItems)
            binding.tvSerialsLabel.visibility = View.VISIBLE
            binding.rvSerials.visibility = View.VISIBLE
        } else {
            binding.tvSerialsLabel.visibility = View.GONE
            binding.rvSerials.visibility = View.GONE
        }

        // Фильтрованные
        if (state.filteredItems.isNotEmpty()) {
            filteredAdapter.submitList(state.filteredItems)
            binding.tvFilteredLabel.visibility = View.VISIBLE
            binding.rvFiltered.visibility = View.VISIBLE
        } else {
            binding.tvFilteredLabel.visibility = View.GONE
            binding.rvFiltered.visibility = View.GONE
        }

        // Показываем ошибку
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
                R.id.action_mainFragment_to_itemInfoFragment,
                bundle
            )
        } catch (e: Exception) {
            showError(e.message ?: "Ошибка навигации")
        }
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