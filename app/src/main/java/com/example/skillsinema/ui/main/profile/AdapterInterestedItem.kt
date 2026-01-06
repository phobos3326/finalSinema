package com.example.skillsinema.ui.main.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.skillsinema.dao.InterestedItemEntity
import com.example.skillsinema.databinding.ItemInterestedBinding // You will need to create this layout file
import com.example.skillsinema.ui.main.home.TypeItem

class AdapterInterestedItem(
    private val onClick: (InterestedItemEntity, TypeItem) -> Unit
) : ListAdapter<InterestedItemEntity, AdapterInterestedItem.InterestedViewHolder>(DiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InterestedViewHolder {
        val binding = ItemInterestedBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return InterestedViewHolder(binding)
    }

    override fun onBindViewHolder(holder: InterestedViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        // You need to determine the TypeItem. For now, let's assume it's always FILM as a placeholder.
        // You might need to add a 'type' field to your InterestedItemEntity.
        holder.itemView.setOnClickListener {
            onClick(item, TypeItem.FILM) // Adjust this logic as needed
        }
    }

    class InterestedViewHolder(private val binding: ItemInterestedBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: InterestedItemEntity) {
            // Here you would bind data from 'item' to your views in 'item_interested.xml'
            // For example:
            // binding.textViewTitle.text = item.title
            // binding.textViewGenre.text = item.genre
            // Use Glide or Coil to load an image if you have one
        }
    }

    class DiffUtilCallback : DiffUtil.ItemCallback<InterestedItemEntity>() {
        override fun areItemsTheSame(oldItem: InterestedItemEntity, newItem: InterestedItemEntity): Boolean {
            return oldItem.idItem == newItem.idItem
        }

        override fun areContentsTheSame(oldItem: InterestedItemEntity, newItem: InterestedItemEntity): Boolean {
            return oldItem == newItem
        }
    }
}
