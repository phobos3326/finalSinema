package com.example.skillsinema.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.skillsinema.databinding.GalerieItemBinding
import com.example.skillsinema.domain.model.GalleryImage
import javax.inject.Inject

class GalerieAdapter @Inject constructor() :
    PagingDataAdapter<GalleryImage, GalerieAdapter.MyViewHolder>(DiffUtilCallback()) {

    class DiffUtilCallback : DiffUtil.ItemCallback<GalleryImage>() {
        override fun areItemsTheSame(oldItem: GalleryImage, newItem: GalleryImage): Boolean =
            oldItem.imageUrl == newItem.imageUrl
        override fun areContentsTheSame(oldItem: GalleryImage, newItem: GalleryImage): Boolean =
            oldItem.imageUrl == newItem.imageUrl
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = GalerieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    class MyViewHolder @Inject constructor(
        private val binding: GalerieItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: GalleryImage) {
            Glide.with(binding.poster)
                .load(item.previewUrl)
                .into(binding.poster)
        }
    }
}
