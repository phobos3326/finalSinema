package com.example.skillsinema.ui.main.home

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView


import com.bumptech.glide.Glide

import com.example.skillsinema.adapter.Film
import com.example.skillsinema.ui.main.home.AdapterBestFilm.Const.END
import com.example.skillsinema.ui.main.home.AdapterBestFilm.Const.NOEND
import com.example.skillsinema.databinding.ItemBinding
import com.example.skillsinema.databinding.SecondItemBinding

import javax.inject.Inject

class AdapterBestFilm @Inject constructor(
    //private val onClick:(BestFilms.Film)->Unit

) : ListAdapter<Film, RecyclerView.ViewHolder>(DiffUtilCallback()) {


    class DiffUtilCallback : DiffUtil.ItemCallback<Film>() {
        override fun areItemsTheSame(oldItem: Film, newItem: Film): Boolean = oldItem == newItem

        override fun areContentsTheSame(oldItem: Film, newItem: Film): Boolean = oldItem == newItem
    }

    private var premiere: List<Film> = emptyList()

    var myViewType = MyViewType(0, 1, "0", HasEnd.FALSE, "", "", "")

    // lateinit var myViewType: MyViewType
    var data = ArrayDeque(mutableListOf<MyViewType>())


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
        // val item = getOrNull(position)

        /*if (holder is MyViewHolder) {
            holder.bind(getItem(position))
        } else if (holder is MyViewHolder2){
            holder.bind()
        }*/
        //  val viewHolder = holder as MyViewHolder


        /*    when (holder.itemViewType) {
                NOEND -> {
                    val viewHolder = holder as MyViewHolder
                    viewHolder.bind(data[position])
                    viewHolder.itemView.setOnClickListener {

                        //  onClick(item!!)
                    }
                }
                END -> {
                    val viewHolder = holder as MyViewHolder2
                    viewHolder.itemView.setOnClickListener {
                        Toast.makeText(holder.itemView.context, "Clicked: ", Toast.LENGTH_SHORT).show()
                    }
                }
            }*/

        if (getItemViewType(position) == NOEND) {
            val item = getItem(position)
            (holder as MyViewHolder).bind(item)
        } else {
            (holder as MyViewHolder2).bind()
        }


    }

    override fun submitList(list: List<Film>?) {
        val newList = list?.toMutableList()
        if (newList?.size ?: 0 >= 19) {
            val lastItem=list?.get(19)
            if (lastItem != null) {
                newList?.add(lastItem)
            } // Add the header item to the list
        }

        super.submitList(list)
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == itemCount - 1 && itemCount >=19) {
            END
        } else {
            NOEND
        }
    }

    private fun isHeaderPosition(position: Int): Boolean {
        return position == 0
    }

    /*override fun getItemCount(): Int {
        return super.getItemCount() + 1
    }*/


    override fun getItemCount(): Int {
        val itemCount = super.getItemCount()
        return if (itemCount >= 20) {
            itemCount + 1 // Add one for the header item
        } else {
            itemCount
        }
    }


    class MyViewHolder @Inject constructor(

        private var binding1: ItemBinding
    ) : RecyclerView.ViewHolder(binding1.root) {

        fun bind(film: Film) {
            // lateinit var myViewType: MyViewType
            val isEnd = false

            binding1.title.text = film.nameRu
            // binding1.textViewGenre.text = myViewType.genre
            binding1.textViewRating.text = film.rating

            film.let {
                Glide.with(binding1.poster)
                    .load(it.posterUrlPreview)
                    .into(binding1.poster)

            }

            /* binding1.root.setOnClickListener {
                 myViewType.let {
                     onClick(it.item)
                 }
             }*/

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

    enum class HasEnd {
        TRUE, FALSE
    }

    data class MyViewType(
        var itemPosition: Int,
        var typePosition: Int,
        var title: String,
        var hasImage: HasEnd,
        var genre: String,
        var image: String,
        var rating: String


    ) {

        //lateinit var genre:String
//    lateinit var item: Model.Item
    }

    private object Const {
        const val END = 0 // random unique value
        const val NOEND = 1
    }

}

