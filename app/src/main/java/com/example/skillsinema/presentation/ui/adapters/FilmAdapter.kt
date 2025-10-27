package com.example.skillsinema.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.skillsinema.R
import com.example.skillsinema.databinding.FragmentItemInfoBinding

import com.example.skillsinema.domain.model.Film

class FilmAdapter(
    private val onFilmClick: (Int) -> Unit
) : ListAdapter<Film, FilmAdapter.FilmViewHolder>(FilmDiffCallback()) {

    inner class FilmViewHolder(
        private val binding: FragmentItemInfoBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(film: Film) {
            // Установка текста
            binding.filmTextView .text = film.nameRu ?: film.nameEn ?: "Без названия"
            binding.YearGenreTextView .text = film.year ?: ""

            // Загрузка изображения через Coil
            binding.filmPreviewImageView .load(film.posterUrlPreview) {
                crossfade(true)
               // placeholder(R.drawable.ic_placeholder) // Добавьте плейсхолдер
              //  error(R.drawable.ic_error) // Добавьте изображение для ошибки
            }

            // Обработка клика
            binding.root.setOnClickListener {
                onFilmClick(film.kinopoiskId)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmViewHolder {
        val binding = FragmentItemInfoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return FilmViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FilmViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    // DiffUtil для оптимизации производительности
    class FilmDiffCallback : DiffUtil.ItemCallback<Film>() {
        override fun areItemsTheSame(oldItem: Film, newItem: Film): Boolean {
            return oldItem.kinopoiskId == newItem.kinopoiskId
        }

        override fun areContentsTheSame(oldItem: Film, newItem: Film): Boolean {
            return oldItem == newItem
        }
    }
}
