package com.example.skillsinema.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.skillsinema.databinding.ItemBinding
import com.example.skillsinema.databinding.SecondItemBinding
import com.example.skillsinema.entity.Film
import com.example.skillsinema.presentation.ui.model.RVDataType
import com.example.skillsinema.presentation.ui.model.TypeOfAdapter
import com.example.skillsinema.presentation.ui.model.TypeItem
import javax.inject.Inject

class AdapterBestFilm @Inject constructor(
    private val onClick: (Film, TypeItem) -> Unit,
    private val onClickShowAll: (TypeOfAdapter, RVDataType) -> Unit
) : ListAdapter<Film, RecyclerView.ViewHolder>(DiffUtilCallback()) {

    val type = TypeOfAdapter.WITHOUTPAGING
    var rvType = RVDataType.TOP250
    val typeItem = TypeItem.FILM

    class DiffUtilCallback : DiffUtil.ItemCallback<Film>() {
        override fun areItemsTheSame(oldItem: Film, newItem: Film): Boolean = oldItem == newItem

        override fun areContentsTheSame(oldItem: Film, newItem: Film): Boolean = oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val binding2 = SecondItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return if (viewType == Const.NOEND) {
            MyViewHolder(binding)
        } else {
            MyViewHolder2(binding2)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == Const.NOEND) {
            val item = getItem(position)
            if (item != null) {
                (holder as MyViewHolder).bind(item)
            }
            holder.itemView.setOnClickListener {
                onClick(item!!, typeItem)
            }
        } else {
            (holder as MyViewHolder2).bind()
            holder.itemView.setOnClickListener {
                onClickShowAll(type, rvType)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == itemCount - 1 && itemCount >= 19) {
            Const.END
        } else {
            Const.NOEND
        }
    }

    override fun getItemCount(): Int {
        val itemCount = super.getItemCount()
        return if (itemCount >= 20) {
            itemCount + 1
        } else {
            itemCount
        }
    }

    class MyViewHolder @Inject constructor(
        private var binding1: ItemBinding
    ) : RecyclerView.ViewHolder(binding1.root) {
        fun bind(film: Film) {
            binding1.title.text = film.nameRu
            binding1.textViewRating.text = film.rating?.toString() ?: ""
            Glide.with(binding1.poster)
                .load(film.posterUrlPreview)
                .into(binding1.poster)
        }
    }

    class MyViewHolder2 @Inject constructor(
        private var binding2: SecondItemBinding
    ) : RecyclerView.ViewHolder(binding2.root) {
        fun bind() {}
    }

    private object Const {
        const val END = 0
        const val NOEND = 1
    }
}
