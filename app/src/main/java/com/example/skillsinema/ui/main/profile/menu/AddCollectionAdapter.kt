package com.example.skillsinema.ui.main.profile.menu


import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.skillsinema.dao.CollectionsEntity
import com.example.skillsinema.databinding.CollectionRecyclerItemBinding
import javax.inject.Inject

class AddCollectionAdapter @Inject constructor(
    private val onChecked: (CollectionsEntity) -> Unit
) : ListAdapter<CollectionsEntity, RecyclerView.ViewHolder>(DiffUtilCallback()) {



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

        override fun getChangePayload(
            oldItem: CollectionsEntity,
            newItem: CollectionsEntity
        ): Any? {
            return super.getChangePayload(oldItem, newItem)
        }

    }

    class ViewHolder @Inject constructor(
        private val binding: CollectionRecyclerItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        private val checkedItems = SparseBooleanArray()


        var checkBoxStatesArray = SparseBooleanArray()
      //private lateinit var currentItem: CollectionsEntity
      val checkbox = binding.checkBox

        init {
           /* checkbox.setOnClickListener {
                if(!checkBoxStatesArray.get(adapterPosition, false)){
                    checkbox.isChecked = true
                    checkBoxStatesArray.put(adapterPosition, true)
                }else{
                    checkbox.isChecked = false
                    checkBoxStatesArray.put(adapterPosition, false)
                }
            }*/

            checkbox.setOnClickListener { v->
                val isChecked = (v as CheckBox).isChecked

            }

        }
        fun bind(item: CollectionsEntity) {
            binding.checkBox.text = item.collectionName
            //checkedItems.put(bindingAdapterPosition, isChecked)
            binding.collectionNameTextView.text = item.collection.size.toString()
           // binding.checkBox.isChecked = checkedItems.get(adapterPosition, false)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = CollectionRecyclerItemBinding.inflate(LayoutInflater.from(parent.context))

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {


        (holder as ViewHolder).bind(getItem(position))

        val item = getItem(position)

      //  holder.checkBox.isChecked = isChecked(position)

        holder.checkbox.setOnCheckedChangeListener { _, isChecked ->

            onChecked(item)
        }

    }







}