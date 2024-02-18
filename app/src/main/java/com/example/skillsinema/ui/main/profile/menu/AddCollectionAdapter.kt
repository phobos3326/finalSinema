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
import com.example.skillsinema.ui.main.profile.CollectionAdapter
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
    private val checkedItems = SparseBooleanArray()
    class ViewHolder @Inject constructor(
        private val binding: CollectionRecyclerItemBinding,
        private val checkedItems: SparseBooleanArray
    ) : RecyclerView.ViewHolder(binding.root) {

       // private val checkedItems = SparseBooleanArray()


      // lateinit var currentItem: CollectionsEntity
      val checkbox = binding.checkBox



        init {

           // binding.collectionSizeTextView.text = currentItem.collection.size.toString()
            checkbox.setOnClickListener { v->
                val isChecked = (v as CheckBox).isChecked
                checkedItems.put(bindingAdapterPosition, isChecked)

            }

        }
        fun bind(item: CollectionsEntity) {

            //binding.checkBox.text = item.collectionName
            //checkedItems.put(bindingAdapterPosition, isChecked)
            binding.collectionSizeTextView.text = item.collection?.size.toString()
            binding.collectionNameTextView.text = item.collectionName

           binding.checkBox.isChecked = checkedItems.get(adapterPosition, false)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = CollectionRecyclerItemBinding.inflate(LayoutInflater.from(parent.context))

        return ViewHolder(binding, checkedItems)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {


      /*  (holder as ViewHolder).bind(getItem(position))

        val item = getItem(position)

      //  holder.checkBox.isChecked = isChecked(position)

        holder.checkbox.setOnCheckedChangeListener { _, isChecked ->

            onChecked(item)
        }*/

        val item = getItem(position)
       // val viewHolder = holder as ViewHolder
        (holder as ViewHolder).bind(getItem(position))
       // holder.bind(item)
        holder.checkbox.isChecked = checkedItems.get(position, false)

        holder.checkbox.setOnCheckedChangeListener { _, isChecked ->
            onChecked(item)
        }

    }







}