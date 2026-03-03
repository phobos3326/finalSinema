package com.example.skillsinema.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.skillsinema.databinding.ItemBinding
import com.example.skillsinema.domain.model.Film
import com.example.skillsinema.presentation.ui.model.TypeItem

class AdapterPagedFilm(
    private val onClick: (Film, TypeItem) -> Unit
) : PagingDataAdapter<Film, RecyclerView.ViewHolder>(DiffUtilCallback()) {

    val typeItem = TypeItem.FILM

    class DiffUtilCallback : DiffUtil.ItemCallback<Film>() {
        override fun areItemsTheSame(oldItem: Film, newItem: Film): Boolean = oldItem == newItem

        override fun areContentsTheSame(oldItem: Film, newItem: Film): Boolean = oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        (holder as MyViewHolder).bind(item!!)
        holder.itemView.setOnClickListener {
            onClick(item, typeItem)
        }
    }

    class MyViewHolder(
        private var binding1: ItemBinding
    ) : RecyclerView.ViewHolder(binding1.root) {
        fun bind(film: Film) {
            binding1.title.text = film.nameRu
            binding1.textViewRating.text = film.rating.toString()
            Glide.with(binding1.poster)
                .load(film.posterUrlPreview)
                .into(binding1.poster)
        }
    }
}
