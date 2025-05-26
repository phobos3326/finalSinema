package com.example.skillsinema.ui.main.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.skillsinema.dao.CollectionsEntity
import com.example.skillsinema.databinding.CollectionItemBinding
import com.example.skillsinema.ui.main.ItemInfo.AdapterEpisodes
import com.example.skillsinema.ui.main.home.RVDataType
import javax.inject.Inject

class CollectionAdapter @Inject constructor(
    private val onClick:(CollectionsEntity, RVDataType)-> Unit,
    private val onDelete:(CollectionsEntity)->Unit
) :
    ListAdapter<CollectionsEntity, RecyclerView.ViewHolder>(DiffUtilCallback()) {

    var rvType = RVDataType.COLLECTION
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
        val close = binding.imageViewClose
        fun bind(item: CollectionsEntity){
            binding.textView4.text = item.collectionName
            binding.itemsCountTextView.text = item.collection?.size.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = CollectionItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)

    }



    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        (holder as ViewHolder).bind(getItem(position))
        //val viewHolder= holder as ViewHolder
       holder.close.setOnClickListener {
            onDelete(item)
        }

        holder.itemView.setOnClickListener{
            onClick(item, rvType)
        }




    }


}