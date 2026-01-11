package com.example.skillsinema.ui.main.showAll

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.skillsinema.R
import com.example.skillsinema.databinding.FragmentShowAllBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ShowAllFragment : Fragment() {

    private var _binding: FragmentShowAllBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ShowAllViewModel by viewModels()
    private lateinit var showAllAdapter: AdapterPagedFilm

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
        setupAdapter()
        observePaging()
    }

    private fun setupAdapter() {
        showAllAdapter = AdapterPagedFilm { film, _ ->
            // подставь реальный id из domain Film
            navigateToFilmDetails(film.kinopoiskId)
        }

        binding.SHOWALLRecyclerView.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = showAllAdapter
        }
    }

    private fun observePaging() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.filmsPaging(category).collectLatest { pagingData ->
                    showAllAdapter.submitData(pagingData)
                }
            }
        }
    }

    private fun navigateToFilmDetails(filmId: Int) {
        try {
            val bundle = Bundle().apply { putInt("film_id", filmId) }
            findNavController().navigate(R.id.action_showAllFragment_to_itemInfoFragment, bundle)
        } catch (e: Exception) {
            showError(e.message ?: "Ошибка навигации")
        }
    }

    private fun showError(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG)
            .setAction("OK") {}
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
