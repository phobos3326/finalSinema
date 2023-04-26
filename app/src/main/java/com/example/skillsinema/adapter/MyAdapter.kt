package com.example.skillsinema.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.skillsinema.adapter.Const.END
import com.example.skillsinema.adapter.Const.NOEND
import com.example.skillsinema.databinding.ItemBinding
import com.example.skillsinema.databinding.SecondItemBinding
import com.example.skillsinema.entity.Model

import javax.inject.Inject


class MyAdapter @Inject constructor(private val onClick: (Model.Item) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var premiere: List<Model.Item> = emptyList()
    var myViewType = MyViewType(0, 1, "0", hasImage = HasEnd.FALSE)
    var data = mutableListOf<MyViewType>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val binding2 = SecondItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return if (viewType == NOEND) {
            MyViewHolder(binding,onClick)
        } else {
            MyViewHolder2(binding2)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = data.getOrNull(position)

        if (getItemViewType(position) == NOEND) {
            (holder as MyViewHolder).bind(data[position])
        } else (holder as MyViewHolder2).bind(data[position])


    }


    override fun getItemCount(): Int {
        return data.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (data[position].hasImage == HasEnd.FALSE) NOEND else END
    }

    fun addToList(prem: List<Model.Item>) {
        this.premiere = prem
        premiere.forEachIndexed { index, it ->
            if (index < 20) {
                myViewType.hasImage = HasEnd.FALSE
                myViewType.title = it.nameRu
                myViewType.itemPosition = index
                myViewType.item = it
                data.add(myViewType.copy())


            } else if (index == 21) {
                myViewType.hasImage = HasEnd.TRUE
                myViewType.title = "посмотреть все"
                data.add(myViewType.copy())
            }
        }
        notifyDataSetChanged()


    }

    companion object {
        const val VIEW_TYPE_1 = 0
        const val VIEW_TYPE_2 = 1
    }
}

class MyViewHolder @Inject constructor(
    private val onClick: (Model.Item) -> Unit,
    private var binding1: ItemBinding
) : RecyclerView.ViewHolder(binding1.root) {
    fun bind(myViewType: MyViewType) {
        myViewType.typePosition = NOEND
        myViewType.hasImage = HasEnd.FALSE
        binding1.textView.text = myViewType.title

       /* binding1.root.setOnClickListener {
            myViewType.let {
                onClick(it.item)
            }
        }*/

    }


}

class MyViewHolder2 @Inject constructor(

    private var binding2: SecondItemBinding
) :
    RecyclerView.ViewHolder(binding2.root) {
    fun bind(myViewType: MyViewType) {
        myViewType.typePosition = END
        myViewType.hasImage = HasEnd.TRUE
        //binding2.textView2.text = myViewType.title


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

    ) {
    lateinit var item: Model.Item
}

private object Const {
    const val END = 0 // random unique value
    const val NOEND = 1
}