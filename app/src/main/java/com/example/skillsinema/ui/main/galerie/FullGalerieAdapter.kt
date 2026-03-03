package com.example.skillsinema.ui.main.galerie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.skillsinema.databinding.GalerieItemBinding
import com.example.skillsinema.domain.model.GalleryImage

class FullGalerieAdapter(
    private val onClick: (String) -> Unit
) : PagingDataAdapter<GalleryImage, FullGalerieAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = GalerieItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) } ?: holder.clear()
    }

    class ViewHolder(
        private val binding: GalerieItemBinding,
        private val onClick: (String) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: GalleryImage) {
            Glide.with(binding.poster)
                .load(item.previewUrl ?: item.imageUrl)
                .centerCrop()
                .placeholder(android.R.drawable.ic_dialog_info)
                .into(binding.poster)

            binding.root.setOnClickListener {
                onClick(item.imageUrl)
            }
        }
        
        fun clear() {
            Glide.with(binding.poster.context).clear(binding.poster)
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<GalleryImage>() {
        override fun areItemsTheSame(oldItem: GalleryImage, newItem: GalleryImage): Boolean =
            oldItem.imageUrl == newItem.imageUrl
        override fun areContentsTheSame(oldItem: GalleryImage, newItem: GalleryImage): Boolean =
            oldItem.imageUrl == newItem.imageUrl
    }
}
