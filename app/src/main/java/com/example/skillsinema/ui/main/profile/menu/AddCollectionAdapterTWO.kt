package com.example.skillsinema.ui.main.profile.menu

import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.skillsinema.dao.CollectionsEntity
import com.example.skillsinema.databinding.CollectionRecyclerItemBinding
import javax.inject.Inject

class AddCollectionAdapterTWO @Inject constructor(
    private val onChecked: (CollectionsUiModel) -> Unit,
    //private val onDelete:(CollectionsEntity)->Unit
) : ListAdapter<CollectionsUiModel, RecyclerView.ViewHolder>(DiffUtilCallback()) {

    private val checkedItems = SparseBooleanArray()


    class DiffUtilCallback : DiffUtil.ItemCallback<CollectionsUiModel>() {
        override fun areItemsTheSame(
            oldItem: CollectionsUiModel,
            newItem: CollectionsUiModel
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: CollectionsUiModel,
            newItem: CollectionsUiModel
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
        val item = getItem(position)
        (holder as ViewHolder).bind(getItem(position))
        holder.checkbox.setOnCheckedChangeListener { _, isChecked ->
            onChecked(item)
        }



    }


    class ViewHolder(private val binding: CollectionRecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val checkbox = binding.checkBox
        fun bind(item: CollectionsUiModel) {
            binding.collectionNameTextView.text = item.collectionName
            binding.collectionSizeTextView.text = item.collection?.size.toString()
            binding.checkBox.isChecked = item.isChecked




           /* binding.checkBox.setOnCheckedChangeListener { _, isChecked ->
                onChecked(item)
            }*/

        }
    }


}