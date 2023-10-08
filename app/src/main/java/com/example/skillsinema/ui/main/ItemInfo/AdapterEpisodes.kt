package com.example.skillsinema.ui.main.ItemInfo

import android.view.LayoutInflater
import android.view.ViewGroup

import com.example.skillsinema.entity.ModelSeasons

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.skillsinema.databinding.SeasonListItemBinding
import javax.inject.Inject

class AdapterEpisodes @Inject constructor() :
    ListAdapter<ModelSeasons.Item.Episode, RecyclerView.ViewHolder>(DiffUtilCallback()) {


    class DiffUtilCallback : DiffUtil.ItemCallback<ModelSeasons.Item.Episode>() {
        override fun areItemsTheSame(
            oldItem: ModelSeasons.Item.Episode,
            newItem: ModelSeasons.Item.Episode
        ): Boolean = oldItem == newItem

        override fun areContentsTheSame(
            oldItem: ModelSeasons.Item.Episode,
            newItem: ModelSeasons.Item.Episode
        ): Boolean = oldItem == newItem
    }

    class ViewHolder @Inject constructor(
        private val binding: SeasonListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ModelSeasons.Item.Episode) {
            binding.textView5.text = "${item.episodeNumber} серия. ${item.nameRu} "
            binding.textView6.text = item.releaseDate
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding =
            SeasonListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return super.getItemCount()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bind(getItem(position))
    }


}