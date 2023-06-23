package com.example.skillsinema.ui.main.home

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.skillsinema.adapter.BestFilms
import com.example.skillsinema.adapter.Film
import com.example.skillsinema.ui.main.home.AdapterBestFilm.Const.END
import com.example.skillsinema.ui.main.home.AdapterBestFilm.Const.NOEND
import com.example.skillsinema.databinding.ItemBinding
import com.example.skillsinema.databinding.SecondItemBinding
import kotlinx.coroutines.delay
import javax.inject.Inject

class AdapterBestFilm @Inject constructor(
    //private val onClick:(BestFilms.Film)->Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    // private  var premiere: MutableList<Film> = emptyList()

    var myViewType = MyViewType(0, 1, "0", HasEnd.FALSE, "", "", "")

    // lateinit var myViewType: MyViewType
    var data = ArrayDeque(mutableListOf<MyViewType>())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val binding2 = SecondItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return if (viewType == NOEND) {
            MyViewHolder(binding)
        } else {
            MyViewHolder2(binding2)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        //val item = premiere.getOrNull(position)


        when (holder.itemViewType) {
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
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun getItemViewType(position: Int): Int {

        if (data[position].hasImage == HasEnd.FALSE) return NOEND else return END
    }


    suspend fun addToList(prem: List<Film>) {
repeat(21){
    data.add(myViewType.copy())
}


        val premiere = prem.toMutableList()



      //if (premiere.isEmpty()){


          premiere.forEachIndexed { index, it ->


              // val lastIndex = it
              // if (index < 20) {
              myViewType.hasImage = HasEnd.FALSE
              myViewType.title = it.nameRu
              myViewType.itemPosition = index
              myViewType.rating = it.rating
              //myViewType. = it
              myViewType.image = it.posterUrlPreview

              val sb = StringBuilder()
              it.genres.forEach {
                  sb.append(it)
              }
              myViewType.genre = sb.toString()

              Log.d("TAGGENRE", it.genres.joinToString())
              //data.add (myViewType.copy())
              data[index] = myViewType.copy()


       //   }


      }

       /* myViewType.hasImage = HasEnd.TRUE
        myViewType.title = "посмотреть все"
        data.add( myViewType.copy())*/

    }

    companion object {
        const val VIEW_TYPE_1 = 0
        const val VIEW_TYPE_2 = 1
    }

    class MyViewHolder @Inject constructor(
        //private val onClick: (Model.Item) -> Unit,
        private var binding1: ItemBinding
    ) : RecyclerView.ViewHolder(binding1.root) {
        //lateinit var myViewType: MyViewType
        fun bind(myViewType: MyViewType) {
            // lateinit var myViewType: MyViewType
            myViewType.typePosition = NOEND
            myViewType.hasImage = HasEnd.FALSE
            binding1.title.text = myViewType.title
            binding1.textViewGenre.text = myViewType.genre
            binding1.textViewRating.text = myViewType.rating

            myViewType.let {
                Glide.with(binding1.poster)
                    .load(it.image)
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

