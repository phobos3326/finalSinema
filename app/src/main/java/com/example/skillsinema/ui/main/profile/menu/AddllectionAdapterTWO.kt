package com.example.skillsinema.ui.main.profile.menu

import android.os.Parcelable
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.skillsinema.dao.CollectionsEntity
import com.example.skillsinema.databinding.CollectionRecyclerItemBinding
import javax.inject.Inject

class AddllectionAdapterTWO @Inject constructor(
    private val onChecked: (CollectionsEntity) -> Unit
) : ListAdapter<CollectionsEntity, RecyclerView.ViewHolder>(DiffUtilCallback()) {

    private val checkedItems = SparseBooleanArray()


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


    fun getCheckedItems(): SparseBooleanArray {
        return checkedItems
    }

    private fun isChecked(position: Int): Boolean {
        return checkedItems[position]
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = CollectionRecyclerItemBinding.inflate(LayoutInflater.from(parent.context))

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder)
        val item = getItem(position)
        holder.bind(item, isChecked(position))


    }


    inner class ViewHolder(private val binding: CollectionRecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CollectionsEntity, isChecked: Boolean) {
            binding.collectionNameTextView.text = item.collectionName
            binding.checkBox.isChecked = isChecked

            binding.checkBox.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    checkedItems.put(adapterPosition, true)
                } else {
                    checkedItems.delete(adapterPosition)
                }
            }
            binding.checkBox.setOnCheckedChangeListener { _, isChecked ->

                onChecked(item)
            }

        }
    }


}