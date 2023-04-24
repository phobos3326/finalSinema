package com.example.skillsinema.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.skillsinema.databinding.ItemBinding
import com.example.skillsinema.entity.Model

import kotlinx.coroutines.NonDisposableHandle
import kotlinx.coroutines.NonDisposableHandle.parent
import javax.inject.Inject

class Adapter @Inject constructor():RecyclerView.Adapter<Adapter.ViewHolder>() {



    private var data:List<Model.Item> = emptyList()

fun setData(premiere: List<Model.Item>){
    this.data =premiere
    notifyDataSetChanged()
}

    class ViewHolder(val binding: ItemBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Adapter.ViewHolder {
        val binding = ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: Adapter.ViewHolder, position: Int) {
        val item =data.getOrNull(position)
        with(holder.binding){
            textView.text=item?.nameRu
        }
    }

    override fun getItemCount(): Int {
       return data.size
    }
}