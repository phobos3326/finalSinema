package com.example.skillsinema.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
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
class MyAdapter : RecyclerView.Adapter<MyViewHolder>() {
    var data = mutableListOf<String>()

    //var photoUrl=""
    fun setData() {
        addToList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        //binding.itemView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT)


        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int, ) {
        val item = data.getOrNull(position)


            holder.binding.textView.text = position.toString()




    }



    override fun getItemCount(): Int {
        return data.size
    }

    fun addToList() {
        for (item in 1..20) {
            data.add("item $item")
        }
    }

}

class MyViewHolder(var binding: ItemBinding) : RecyclerView.ViewHolder(binding.root)
class MyViewHolde2(var binding: SecondItemBinding) : RecyclerView.ViewHolder(binding.root)

enum class HasEnd {
    TRUE, FALSE
}