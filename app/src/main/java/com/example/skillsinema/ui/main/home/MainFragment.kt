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

    private lateinit var premiereAdapter: FilmAdapter
    private lateinit var topFilmsAdapter: FilmAdapter
    private lateinit var serialsAdapter: FilmAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // ViewBinding инициализация
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
        // Инициализация адаптеров с лямбда-функциями
        premiereAdapter = FilmAdapter { filmId ->
            navigateToFilmDetails(filmId)
        }

        topFilmsAdapter = FilmAdapter { filmId ->
            navigateToFilmDetails(filmId)
        }

        serialsAdapter = FilmAdapter { filmId ->
            navigateToFilmDetails(filmId)
        }

        // Настройка RecyclerView через ViewBinding
        binding.viewPager .apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = premiereAdapter
        }

        binding.TopFilmsRecyclerView .apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = topFilmsAdapter
        }

        binding.serialsRecyclerView .apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = serialsAdapter
        }
    }

    private fun observeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            // Простое наблюдение за состоянием (без Flow)
            while (true) {
                renderState(viewModel.state)
                kotlinx.coroutines.delay(100) // Проверяем каждые 100мс
            }
        }
    }

    private fun renderState(state: MainUiState) {
        // Управление ProgressBar через ViewBinding
        //binding.progressLoading.visibility = if (state.isLoading) View.VISIBLE else View.GONE

        // Обновление адаптеров
        if (state.premieres.isNotEmpty()) {
            premiereAdapter.submitList(state.premieres)
        }

        if (state.topFilms.isNotEmpty()) {
            topFilmsAdapter.submitList(state.topFilms)
        }

        if (state.serials.isNotEmpty()) {
            serialsAdapter.submitList(state.serials)
        }

        // Показ ошибок
        state.error?.let { error ->
            showError(error)
        }

        // Управление видимостью секций
        //updateSectionVisibility(state)
    }

 /*   private fun updateSectionVisibility(state: MainUiState) {
        // ViewBinding обращение к View элементам
        binding.tvPremieresLabel.visibility =
            if (state.premieres.isNotEmpty()) View.VISIBLE else View.GONE
        binding.rvPremieres.visibility =
            if (state.premieres.isNotEmpty()) View.VISIBLE else View.GONE

        binding.tvTopFilmsLabel.visibility =
            if (state.topFilms.isNotEmpty()) View.VISIBLE else View.GONE
        binding.rvTopFilms.visibility =
            if (state.topFilms.isNotEmpty()) View.VISIBLE else View.GONE

        binding.tvSerialsLabel.visibility =
            if (state.serials.isNotEmpty()) View.VISIBLE else View.GONE
        binding.rvSerials.visibility =
            if (state.serials.isNotEmpty()) View.VISIBLE else View.GONE
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
        // Snackbar через ViewBinding
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
        // ViewBinding для onClick
        binding.viewPager.setOnClickListener {
            navigateToShowAll("premieres")
        }

        binding.TopFilmsRecyclerView .setOnClickListener {
            navigateToShowAll("top_films")
        }

        binding.serialsRecyclerView .setOnClickListener {
            navigateToShowAll("serials")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Очистка ViewBinding для предотвращения утечек памяти
        _binding = null
    }
}
