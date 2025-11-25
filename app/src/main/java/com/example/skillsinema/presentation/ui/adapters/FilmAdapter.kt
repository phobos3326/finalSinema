package com.example.skillsinema.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.skillsinema.R

import com.example.skillsinema.databinding.ItemBinding
import com.example.skillsinema.databinding.SecondItemBinding

import com.example.skillsinema.domain.model.Film

class FilmAdapter(
    private val onFilmClick: (Int) -> Unit,
    private val onShowAllClick: (String) -> Unit
) : ListAdapter<FilmListItem, RecyclerView.ViewHolder>(FilmDiffCallback()) {

    companion object {
        private const val TYPE_FILM = 0
        private const val TYPE_SHOW_ALL = 1
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is FilmListItem.FilmItem -> TYPE_FILM
            is FilmListItem.ShowAllItem -> TYPE_SHOW_ALL
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_FILM -> {
                val binding = ItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                FilmViewHolder(binding)
            }
            TYPE_SHOW_ALL -> {
                val binding = SecondItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                ShowAllViewHolder(binding)
            }
            else -> throw IllegalArgumentException("Unknown view type: $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = getItem(position)) {
            is FilmListItem.FilmItem -> {
                (holder as FilmViewHolder).bind(item.film)
            }
            is FilmListItem.ShowAllItem -> {
                (holder as ShowAllViewHolder).bind(item.category)
            }
        }
    }

    // ViewHolder для фильма
    inner class FilmViewHolder(
        private val binding: ItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(film: Film) {
            binding.title.text = film.nameRu ?: film.nameEn ?: "Без названия"
            binding.textViewGenre .text = film.year ?: ""

            // Правильная обработка жанров
            val genresText = when {
                film.genres.isNotEmpty() -> film.genres.joinToString(", ") { it.genre }
                else -> ""
            }
            // Если есть tvGenres в layout
             binding.textViewGenre .text = genresText

            binding.poster .load(film.posterUrlPreview) {
                crossfade(true)
                /*placeholder(R.drawable.ic_placeholder)
                error(R.drawable.ic_error)*/
            }

            binding.root.setOnClickListener {
                onFilmClick(film.kinopoiskId)
            }
        }
    }

    // ViewHolder для кнопки "Показать все"
    inner class ShowAllViewHolder(
        private val binding: SecondItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(category: String) {
            binding.root.setOnClickListener {
                onShowAllClick(category)
            }
        }
    }

    class FilmDiffCallback : DiffUtil.ItemCallback<FilmListItem>() {
        override fun areItemsTheSame(oldItem: FilmListItem, newItem: FilmListItem): Boolean {
            return when {
                oldItem is FilmListItem.FilmItem && newItem is FilmListItem.FilmItem ->
                    oldItem.film.kinopoiskId == newItem.film.kinopoiskId
                oldItem is FilmListItem.ShowAllItem && newItem is FilmListItem.ShowAllItem ->
                    oldItem.category == newItem.category
                else -> false
            }
        }

        override fun areContentsTheSame(oldItem: FilmListItem, newItem: FilmListItem): Boolean {
            return oldItem == newItem
        }
    }
}