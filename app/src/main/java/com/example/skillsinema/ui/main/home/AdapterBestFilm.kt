package com.example.skillsinema.ui.main.home

import android.view.LayoutInflater
import android.view.View.OnClickListener
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.navigation.findNavController

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView


import com.bumptech.glide.Glide
import com.example.skillsinema.R

import com.example.skillsinema.entity.Film
import com.example.skillsinema.ui.main.home.AdapterBestFilm.Const.END
import com.example.skillsinema.ui.main.home.AdapterBestFilm.Const.NOEND
import com.example.skillsinema.databinding.ItemBinding
import com.example.skillsinema.databinding.SecondItemBinding

import javax.inject.Inject

class AdapterBestFilm @Inject constructor(
    private val onClick: (Film) -> Unit,
    private val onClickShowAll:(TypeOfAdapter, RVDataType) ->Unit

) : ListAdapter<Film, RecyclerView.ViewHolder>(DiffUtilCallback()) {

    val type = TypeOfAdapter.WITHOUTPAGING
    var rvType = RVDataType.TOP250

    private var onClickListener: OnClickListener? = null

    class DiffUtilCallback : DiffUtil.ItemCallback<Film>() {
        override fun areItemsTheSame(oldItem: Film, newItem: Film): Boolean = oldItem == newItem

        override fun areContentsTheSame(oldItem: Film, newItem: Film): Boolean = oldItem == newItem
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val binding2 = SecondItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return if (viewType == NOEND) {
            return MyViewHolder(binding)
        } else {
            MyViewHolder2(binding2)
        }

    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == NOEND) {
            val item = getItem(position)
            if (item != null) {
                (holder as MyViewHolder).bind(item)
            }
            holder.itemView.setOnClickListener {
                onClick(item!!)


            }
        } else {
            (holder as MyViewHolder2).bind()
            holder.itemView.setOnClickListener {
                //it.findNavController().navigate(R.id.action_home_fragment_to_showAllFragment)
                onClickShowAll(type, rvType)
            }
        }
    }





   /* override fun submitList(list: List<Film>?) {
        val newList = list?.toMutableList()
        if ((newList?.size ?: 0) >= 19) {
            val lastItem = list?.get(19)
            if (lastItem != null) {
                newList?.add(lastItem)
            }
        }

        super.submitList(list)
    }*/

    override fun getItemViewType(position: Int): Int {
        return if (position == itemCount - 1 && itemCount >= 19) {
            END
        } else {
            NOEND
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
            binding1.textViewRating.text = film.rating
            if (film.isViewed==true){
                binding1.isViewedImageView.visibility = VISIBLE
            }
            film.let {
                Glide.with(binding1.poster)

                    .load(it.posterUrlPreview)
                    //.addListener()
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

