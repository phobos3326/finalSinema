package com.example.skillsinema.ui.main.galerie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.skillsinema.databinding.GalerieItemBinding

import com.example.skillsinema.entity.ModelGalerie

class FullGalerieAdapter(
    private val onClick: (String) -> Unit
) : PagingDataAdapter<ModelGalerie.Item, FullGalerieAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = GalerieItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bind(item)
        }
    }

    class ViewHolder(
        private val binding: GalerieItemBinding,
        private val onClick: (String) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ModelGalerie.Item) {
            // Загружаем изображение
            Glide.with(binding.root.context)
                .load(item.imageUrl)
                .centerCrop()
                .into(binding.poster)

            // Клик на изображение
            binding.itemView .setOnClickListener {
                onClick(item.imageUrl)
            }

            // Можно также сделать клик на весь элемент
            binding.root.setOnClickListener {
                onClick(item.imageUrl)
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<ModelGalerie.Item>() {
        override fun areItemsTheSame(
            oldItem: ModelGalerie.Item,
            newItem: ModelGalerie.Item
        ): Boolean {
            return oldItem.imageUrl == newItem.imageUrl
        }

        override fun areContentsTheSame(
            oldItem: ModelGalerie.Item,
            newItem: ModelGalerie.Item
        ): Boolean {
            return oldItem == newItem
        }
    }
}
