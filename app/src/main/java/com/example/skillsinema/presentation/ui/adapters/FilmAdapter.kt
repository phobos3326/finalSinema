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
                // ← ViewBinding (НЕ DataBinding):
                val binding = ItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                FilmViewHolder(binding)
            }
            TYPE_SHOW_ALL -> {
                // ← ViewBinding (НЕ DataBinding):
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

    // ViewHolder для фильма с ViewBinding
    inner class FilmViewHolder(
        private val binding: ItemBinding  // ← ViewBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(film: Film) {
            // ← Прямое обращение к View через ViewBinding:
            binding.title .text = film.nameRu ?: film.nameEn ?: "Без названия"
            binding.textViewGenre .text = (film.genres ?: "") as CharSequence?

            // ← Загрузка изображения через ViewBinding:
            binding.poster .load(film.posterUrlPreview) {
                crossfade(true)
               /* placeholder(R.drawable.ic_placeholder)
                error(R.drawable.ic_error)*/
            }

            // ← Клик через ViewBinding:
            binding.root.setOnClickListener {
                onFilmClick(film.kinopoiskId)
            }
        }
    }

    // ViewHolder для кнопки "Показать все" с ViewBinding
    inner class ShowAllViewHolder(
        private val binding: SecondItemBinding  // ← ViewBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(category: String) {
            val categoryText = when (category) {
                "premieres" -> "премьеры"
                "top_films" -> "топ"
                "serials" -> "сериалы"
                "filtered" -> "подборку"
                else -> category
            }

            // ← Обращение к TextView через ViewBinding:
            //binding. .text = "Показать все"
           // binding.tvCategory.text = categoryText

            // ← Клик через ViewBinding:
            binding.root.setOnClickListener {
                onShowAllClick(category)
            }
        }
    }

    // DiffUtil для оптимизации
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