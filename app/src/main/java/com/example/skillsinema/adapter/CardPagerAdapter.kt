package com.example.skillsinema.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.skillsinema.adapter.Const.END
import com.example.skillsinema.adapter.Const.NOEND
import com.example.skillsinema.databinding.ItemBinding
import com.example.skillsinema.databinding.SecondItemBinding

/*
class ViewPagerAdapter() :
    RecyclerView.Adapter<ViewPagerViewHolder>() {

    private var data: List<String> = emptyList()
    fun setData(dataValue: MutableList<String>){
        this.data = dataValue
        notifyDataSetChanged()
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerViewHolder {

        val binding = ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewPagerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewPagerViewHolder, position: Int) {
       val item= data.size
        with(holder.binding){
            textView.text = item.toString()
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    interface ConditionViewPager {

        fun condition(position : Int, fullSize : Int)

    }

}

class ViewPagerViewHolder(val binding: ItemBinding) : RecyclerView.ViewHolder(binding.root)*/
class MyAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var myViewType = MyViewType(1, 0, hasImage = HasEnd.FALSE)
    var data = mutableListOf<MyViewType>()

    //var photoUrl=""
    fun setData() {
        addToList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val binding2 = SecondItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        //binding.itemView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT)

        return if (viewType == NOEND) {
            MyViewHolder(binding)
        } else {
            MyViewHolder2(binding2)
        }
        //return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = data.getOrNull(position)

        if (getItemViewType(position) == NOEND) {
            (holder as MyViewHolder).bind(data[position])
        } else (holder as MyViewHolder2).bind(data[position])

        //holder.binding1.textView.text = position.toString()


    }


    override fun getItemCount(): Int {
        return data.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (data[position].hasImage == HasEnd.FALSE) NOEND else END
    }

    var a = myViewType
    fun addToList() {
        for (item in 1..20) {
            a.title=item
            if (item!=20){
                data.add(myViewType.copy())
            }else{
                a.hasImage= HasEnd.TRUE
                data.add(myViewType.copy())
            }


        }
    }

}

class MyViewHolder(var binding1: ItemBinding) : RecyclerView.ViewHolder(binding1.root) {
    fun bind(myViewType: MyViewType) {
        myViewType.typePosition = NOEND
        myViewType.hasImage = HasEnd.FALSE
        binding1.textView.text = myViewType.title.toString()
    }
}

class MyViewHolder2(var binding2: SecondItemBinding) : RecyclerView.ViewHolder(binding2.root) {
    fun bind(myViewType: MyViewType) {
        myViewType.typePosition = END
        myViewType.hasImage = HasEnd.TRUE
        binding2.textView2.text = myViewType.title.toString()
    }
}

enum class HasEnd {
    TRUE, FALSE
}

data class MyViewType(
    var typePosition: Int,
    var title: Int,
    var hasImage: HasEnd
)

private object Const {
    const val END = 0 // random unique value
    const val NOEND = 1
}