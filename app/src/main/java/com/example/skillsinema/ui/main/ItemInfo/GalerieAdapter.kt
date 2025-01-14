package com.example.skillsinema.ui.main.ItemInfo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.skillsinema.R
import com.example.skillsinema.databinding.GalerieItemBinding
import com.example.skillsinema.databinding.SecondItemBinding


import com.example.skillsinema.entity.ModelGalerie
import com.example.skillsinema.ui.main.ItemInfo.GalerieAdapter.Const.END
import com.example.skillsinema.ui.main.ItemInfo.GalerieAdapter.Const.NOEND
import javax.inject.Inject

class GalerieAdapter @Inject constructor() :
    PagingDataAdapter<ModelGalerie.Item, RecyclerView.ViewHolder>(DiffUtilCallback()) {


    class DiffUtilCallback : DiffUtil.ItemCallback<ModelGalerie.Item>() {
        override fun areItemsTheSame(
            oldItem: ModelGalerie.Item,
            newItem: ModelGalerie.Item
        ): Boolean = oldItem == newItem

        override fun areContentsTheSame(
            oldItem: ModelGalerie.Item,
            newItem: ModelGalerie.Item
        ): Boolean = oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = GalerieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        // val binding2 = SecondItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MyViewHolder(binding)


    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {


        val item = getItem(position)

        (holder as MyViewHolder).bind(item!!)

        holder.itemView.setOnClickListener {
            //   onClick(item!!)


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
    override fun getItemViewType(position: Int): Int {
        return if (position == itemCount - 1 && itemCount >= 19) {
            END
        } else {
            NOEND
        }
    }

    class MyViewHolder @Inject constructor(
        private var binding1: GalerieItemBinding
    ) : RecyclerView.ViewHolder(binding1.root) {
        fun bind(film: ModelGalerie.Item) {

            film.let {
                Glide.with(binding1.poster)
                    .load(it.previewUrl)
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