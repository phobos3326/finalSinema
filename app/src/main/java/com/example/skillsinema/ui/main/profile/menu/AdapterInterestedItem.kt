package com.example.skillsinema.ui.main.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.skillsinema.dao.InterestedItemEntity
import com.example.skillsinema.databinding.ItemInterestedBinding
import com.example.skillsinema.ui.main.home.TypeItem

class AdapterInterestedItem(
    private val onClick: (InterestedItemEntity, TypeItem) -> Unit
) : ListAdapter<InterestedItemEntity, AdapterInterestedItem.VH>(Diff) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ItemInterestedBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return VH(binding, onClick)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(getItem(position))
    }

    class VH(
        private val binding: ItemInterestedBinding,
        private val onClick: (InterestedItemEntity, TypeItem) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: InterestedItemEntity) {
            // Заголовок
            binding.title.text = item.nameRUItem ?: item.nameENItem.orEmpty()

            // Рейтинг (у тебя ratingItem: Int?)
            binding.textViewRating.text = item.ratingItem?.toString().orEmpty()

            // Постер: если у тебя ImageView/ShapeableImageView, то можно так (пример):
            // item.posterItem?.let { bytes ->
            //     val bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
            //     binding.poster.setImageBitmap(bmp)
            // }

            binding.root.setOnClickListener { onClick(item, item.typeItem) }
        }
    }

    private object Diff : DiffUtil.ItemCallback<InterestedItemEntity>() {
        override fun areItemsTheSame(oldItem: InterestedItemEntity, newItem: InterestedItemEntity): Boolean {
            // У тебя уникальный idItem (ID фильма/актёра) + typeItem
            return oldItem.idItem == newItem.idItem && oldItem.typeItem == newItem.typeItem
        }

        override fun areContentsTheSame(oldItem: InterestedItemEntity, newItem: InterestedItemEntity): Boolean {
            return oldItem == newItem
        }
    }
}
