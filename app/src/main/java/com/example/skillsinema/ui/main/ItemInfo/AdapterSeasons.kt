package com.example.skillsinema.ui.main.ItemInfo

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.skillsinema.databinding.SeasonItemBinding
import com.example.skillsinema.entity.Film

import com.example.skillsinema.entity.ModelSeasons
import javax.inject.Inject

class AdapterSeasons @Inject constructor(
    private val onClick: (ModelSeasons.Item) -> Unit
) :
    ListAdapter<ModelSeasons.Item, RecyclerView.ViewHolder>(DiffUtilCallback()) {


    class DiffUtilCallback : DiffUtil.ItemCallback<ModelSeasons.Item>() {
        override fun areItemsTheSame(
            oldItem: ModelSeasons.Item,
            newItem: ModelSeasons.Item
        ): Boolean = oldItem == newItem

        override fun areContentsTheSame(
            oldItem: ModelSeasons.Item,
            newItem: ModelSeasons.Item
        ): Boolean = oldItem == newItem
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = SeasonItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    //override fun getItemCount()= ModelSeasons.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)

        (holder as ViewHolder).bind(item)



        holder.itemView.setOnClickListener{
            onClick(item)
            Log.e("onClickseason", "сезон")
        }
    }

    class ViewHolder @Inject constructor(
        private val binding: SeasonItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ModelSeasons.Item) {
            binding.roundedButton.text = "${item.number} "

        }


    }
}
