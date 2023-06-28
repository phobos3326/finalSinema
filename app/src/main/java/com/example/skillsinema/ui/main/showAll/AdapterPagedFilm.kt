package com.example.skillsinema.ui.main.showAll

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil

import androidx.recyclerview.widget.RecyclerView


import com.bumptech.glide.Glide

import com.example.skillsinema.adapter.Film

import com.example.skillsinema.databinding.ItemBinding
import com.example.skillsinema.databinding.SecondItemBinding
import com.example.skillsinema.entity.Movie
import com.example.skillsinema.ui.main.showAll.AdapterPagedFilm.Const.NOEND

import javax.inject.Inject

class AdapterPagedFilm @Inject constructor(
    private val onClick: (Film) -> Unit,

) : PagingDataAdapter<Film, RecyclerView.ViewHolder>(DiffUtilCallback()) {


    class DiffUtilCallback : DiffUtil.ItemCallback<Film>() {
        override fun areItemsTheSame(oldItem: Film, newItem: Film): Boolean = oldItem == newItem

        override fun areContentsTheSame(oldItem: Film, newItem: Film): Boolean = oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
       // val binding2 = SecondItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

            return MyViewHolder(binding)


    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

            val item = getItem(position)
            (holder as MyViewHolder).bind(item!!)
            holder.itemView.setOnClickListener {
                                       onClick(item!!)

            }

    }

  /*  override fun submitList(list: List<Film>?) {
        val newList = list?.toMutableList()
        if ((newList?.size ?: 0) >= 19) {
            val lastItem = list?.get(19)
            if (lastItem != null) {
                newList?.add(lastItem)
            }
        }

        super.submitList(list)
    }*/

    /*override fun getItemViewType(position: Int): Int {
        return if (position == itemCount - 1 && itemCount >= 19) {
            END
        } else {
            NOEND
        }
    }*/


   /* override fun getItemCount(): Int {
        val itemCount = super.getItemCount()
        return if (itemCount >= 20) {
            itemCount + 1
        } else {
            itemCount
        }
    }*/


    class MyViewHolder @Inject constructor(
        private var binding1: ItemBinding
    ) : RecyclerView.ViewHolder(binding1.root) {
        fun bind(film: Film) {
            binding1.title.text = film.nameRu
            binding1.textViewRating.text = film.rating
            film.let {
                Glide.with(binding1.poster)
                    .load(it.posterUrlPreview)
                    .into(binding1.poster)
            }
        }
    }


    class MyViewHolder2 @Inject constructor(
        private var binding2: SecondItemBinding
    ) : RecyclerView.ViewHolder(binding2.root) {
        fun bind() {
            //myViewType.typePosition = END
            //myViewType.hasImage = HasEnd.TRUE
            // binding2.textView2.text = myViewType.title

        }
    }

    private object Const {
        const val END = 0 // random unique value
        const val NOEND = 1
    }

}

