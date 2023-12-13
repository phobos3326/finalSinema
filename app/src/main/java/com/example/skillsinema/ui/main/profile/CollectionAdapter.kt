package com.example.skillsinema.ui.main.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.skillsinema.dao.CollectionsEntity
import com.example.skillsinema.databinding.CollectionItemBinding
import com.example.skillsinema.ui.main.ItemInfo.AdapterEpisodes
import javax.inject.Inject

class CollectionAdapter @Inject constructor() :
    ListAdapter<CollectionsEntity, RecyclerView.ViewHolder>(DiffUtilCallback()) {
    class DiffUtilCallback : DiffUtil.ItemCallback<CollectionsEntity>() {
        override fun areItemsTheSame(
            oldItem: CollectionsEntity,
            newItem: CollectionsEntity
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: CollectionsEntity,
            newItem: CollectionsEntity
        ): Boolean {
            return oldItem == newItem
        }
    }


    class ViewHolder @Inject constructor(
        private val binding: CollectionItemBinding
    ):RecyclerView.ViewHolder(binding.root){
        fun bind(item: CollectionsEntity){
            binding.textView4.text = item.collectionName
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = CollectionItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bind(getItem(position))
    }


}