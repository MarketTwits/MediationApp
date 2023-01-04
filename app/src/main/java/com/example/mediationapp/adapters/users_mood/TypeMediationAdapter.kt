package com.example.mediationapp.adapters.users_mood


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mediationapp.R
import com.example.mediationapp.model.MeditationElement
import kotlinx.coroutines.NonDisposableHandle.parent
import kotlin.coroutines.coroutineContext

class TypeMediationAdapter(var meditationList : MutableList<MeditationElement>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    //var meditationList = mutableListOf<MeditationElement>()

    interface OnMeditationClickListener {
        fun onMeditationClick()
    }

    interface OnAddClickListener {
        fun onAddClick(meditation: MeditationElement)
    }



    inner class MeditationViewHolder(itemView: View, clickListener : OnMeditationClickListener) :
        RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.im_item_image_mood)
        val textView: TextView = itemView.findViewById(R.id.tv_item_time)

        init {
            itemView.setOnClickListener {
                clickListener.onMeditationClick()
            }
        }
    }

    inner class AddViewHolder(itemView: View, clickListener: OnAddClickListener) :
        RecyclerView.ViewHolder(itemView) {
        // val imageView: ImageView = itemView.findViewById(R.id.item)
       // val textView: TextView = itemView.findViewById(R.id.tv_item_time)

        init {
            itemView.setOnClickListener {
                clickListener.onAddClick(meditationList[bindingAdapterPosition])
            }

        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            VIEW_TYPE_ITEM -> {
                val view = inflater.inflate(R.layout.item_mood_users, parent, false)
                MeditationViewHolder(view, object : OnMeditationClickListener {
                    override fun onMeditationClick() {
                        Toast.makeText(parent.context, "Clicked", Toast.LENGTH_SHORT).show()
                    }
                })

            }
            VIEW_TYPE_ADD -> {
                val view = inflater.inflate(R.layout.item_add_mood_users, parent, false)
                AddViewHolder(view, object : OnAddClickListener {
                    override fun onAddClick(meditation: MeditationElement) {
                        addItem(meditation)
                    }
                })
            }
            else -> throw Exception("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MeditationViewHolder -> {
                val element = meditationList[position]
                holder.textView.text = element.timeAdded
                Glide.with(holder.itemView).load(element.imageUrl).into(holder.imageView)

                holder.itemView.setOnLongClickListener {
                    deleteItem(element, position)
                    true
                }


            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == meditationList.size - 1) {
            VIEW_TYPE_ADD
        } else {
            VIEW_TYPE_ITEM
        }
    }
    fun setMediationList(items : List<MeditationElement>){
        meditationList = items.toMutableList()
        notifyDataSetChanged()
    }
    fun deleteItem(element : MeditationElement, position: Int){
        meditationList.remove(element)
        notifyItemRemoved(position)
    }
    fun addItem(item: MeditationElement) {
        meditationList.add(item)
        notifyItemInserted(meditationList.size + 1)
    }

    override fun getItemCount(): Int {
        return meditationList.size
    }
    
    companion object{
        private val VIEW_TYPE_ITEM = 0
        private val VIEW_TYPE_ADD = 1
    }
}

