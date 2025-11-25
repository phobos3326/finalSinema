package com.example.skillsinema.ui.main.showAll

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.skillsinema.R
import com.example.skillsinema.databinding.ItemBinding
import com.example.skillsinema.domain.model.Film

class ShowAllAdapter(
    private val onFilmClick: (Int) -> Unit
) : ListAdapter<Film, ShowAllAdapter.ShowAllViewHolder>(FilmDiffCallback()) {

    inner class ShowAllViewHolder(
        private val binding: ItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(film: Film) {
            binding.title.text = film.nameRu ?: film.nameEn ?: "Без названия"
            //binding.textViewGenre.text = film.year ?: ""

            // Правильная обработка жанров
            val genresText = when {
                film.genres.isNotEmpty() -> film.genres.joinToString(", ") { it.genre }
                else -> ""
            }
            binding.textViewGenre.text = genresText

            // Рейтинг (если есть)
            val rating = film.rating ?: film.rating
            binding.textViewRating .text = rating?.toString() ?: ""

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowAllViewHolder {
        val binding = ItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ShowAllViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ShowAllViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class FilmDiffCallback : DiffUtil.ItemCallback<Film>() {
        override fun areItemsTheSame(oldItem: Film, newItem: Film): Boolean {
            return oldItem.kinopoiskId == newItem.kinopoiskId
        }

        override fun areContentsTheSame(oldItem: Film, newItem: Film): Boolean {
            return oldItem == newItem
        }
    }
}