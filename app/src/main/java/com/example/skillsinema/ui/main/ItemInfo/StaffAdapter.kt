package com.example.skillsinema.ui.main.ItemInfo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.skillsinema.R
import com.example.skillsinema.databinding.ActorItemBinding
import com.example.skillsinema.databinding.SecondItemBinding
import com.example.skillsinema.entity.Film
import com.example.skillsinema.entity.ModelActorInfo
import com.example.skillsinema.entity.ModelStaff

import com.example.skillsinema.ui.main.ItemInfo.StaffAdapter.Const.END
import com.example.skillsinema.ui.main.ItemInfo.StaffAdapter.Const.NOEND
import com.example.skillsinema.ui.main.home.TypeItem
import javax.inject.Inject

class StaffAdapter @Inject constructor(
    private val onClick: (ModelStaff.ModelStaffItem, TypeItem) -> Unit
)    :ListAdapter<ModelStaff.ModelStaffItem, RecyclerView.ViewHolder>(DiffUtilCallback()) {

    //private var onClickListener: View.OnClickListener? = null
    val typeItem = TypeItem.PERSON
    class DiffUtilCallback : DiffUtil.ItemCallback<ModelStaff.ModelStaffItem>() {
        override fun areItemsTheSame(oldItem: ModelStaff.ModelStaffItem, newItem: ModelStaff.ModelStaffItem): Boolean = oldItem == newItem

        override fun areContentsTheSame(oldItem: ModelStaff.ModelStaffItem, newItem: ModelStaff.ModelStaffItem): Boolean = oldItem == newItem
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ActorItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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
                onClick(item!!,typeItem )


            }
        } else {
            (holder as MyViewHolder2).bind()
            holder.itemView.setOnClickListener {
                it.findNavController().navigate(R.id.action_home_fragment_to_showAllFragment)
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
        private var binding1: ActorItemBinding
    ) : RecyclerView.ViewHolder(binding1.root) {
        fun bind(film: ModelStaff.ModelStaffItem) {
            binding1.title.text = film.nameRu
            binding1.textViewGenre.text = film.professionText
            film.let {
                Glide.with(binding1.poster)
                    .load(it.posterUrl)
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
