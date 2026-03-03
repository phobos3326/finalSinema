package com.example.skillsinema.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.skillsinema.dao.InterestedItemEntity
import com.example.skillsinema.databinding.ItemInterestedBinding
import com.example.skillsinema.presentation.ui.model.TypeItem

class AdapterInterestedItem(
    private val onClick: (InterestedItemEntity, TypeItem) -> Unit
) : ListAdapter<InterestedItemEntity, AdapterInterestedItem.VH>(Diff) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ItemInterestedBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return VH(binding, onClick)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(getItem(position))
    }

    class VH(
        private val binding: ItemInterestedBinding,
        private val onClick: (InterestedItemEntity, TypeItem) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: InterestedItemEntity) {
            binding.title.text = item.nameRUItem ?: item.nameENItem.orEmpty()
            binding.textViewRating.text = item.ratingItem?.toString().orEmpty()
            
            // Загружаем постер из ByteArray
            item.posterItem?.let { imageBytes ->
                Glide.with(binding.poster)
                    .load(imageBytes)
                    .centerCrop()
                    .into(binding.poster)
            }
            
            binding.root.setOnClickListener { onClick(item, item.typeItem) }
        }
    }

    private object Diff : DiffUtil.ItemCallback<InterestedItemEntity>() {
        override fun areItemsTheSame(oldItem: InterestedItemEntity, newItem: InterestedItemEntity): Boolean {
            return oldItem.idItem == newItem.idItem && oldItem.typeItem == newItem.typeItem
        }

        override fun areContentsTheSame(oldItem: InterestedItemEntity, newItem: InterestedItemEntity): Boolean {
            return oldItem == newItem
        }
    }
}
