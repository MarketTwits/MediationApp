package com.example.mediationapp.adapters.users_mood


import android.provider.MediaStore
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
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.NonDisposableHandle.parent
import kotlin.coroutines.coroutineContext

class TypeMediationAdapter(
    ): RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    var list = listOf<MeditationElement>()
        set(value){
            field = value
            notifyDataSetChanged()
        }


    interface OnMeditationClickListener {
        fun onMeditationClick()
    }

    interface OnAddClickListener {
        fun onAddClick(meditation: MeditationElement)
    }

    var onMeditationClickListener : ((MeditationElement) -> Unit)? = null
    var onMediationLongClickListener : ((MeditationElement) -> Unit)? = null
    var onAddClickListener : ((MeditationElement) -> Unit)? = null


    inner class MeditationViewHolder(itemView: View, clickListener : OnMeditationClickListener) :
        RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.im_item_image_mood)
        val textView: TextView = itemView.findViewById(R.id.tv_item_time)

    }

    inner class AddViewHolder(itemView: View, clickListener: OnAddClickListener) :
        RecyclerView.ViewHolder(itemView) {
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            VIEW_TYPE_ITEM -> {
                val view = inflater.inflate(R.layout.item_mood_users, parent, false)
                MeditationViewHolder(view, object : OnMeditationClickListener {
                    override fun onMeditationClick() {
                        //Toast.makeText(parent.context, "Clicked", Toast.LENGTH_SHORT).show()
                    }
                })

            }
            VIEW_TYPE_ADD -> {
                val view = inflater.inflate(R.layout.item_add_mood_users, parent, false)
                AddViewHolder(view, object : OnAddClickListener {
                    override fun onAddClick(meditation: MeditationElement) {
                        //addItem(meditation)
                    }
                })
            }
            else -> throw Exception("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val element = list[position]

        when (holder) {
            is MeditationViewHolder -> {
                holder.textView.text = element.timeAdded
                Glide.with(holder.itemView).load(element.imageUrl).into(holder.imageView)

                holder.itemView.setOnLongClickListener {
                    onMediationLongClickListener?.invoke(element)
                    true
                }
                holder.itemView.setOnClickListener {
                    onMeditationClickListener?.invoke(element)
                }
            }
            is AddViewHolder ->{
                holder.itemView.setOnClickListener {
                    onAddClickListener?.invoke(element)
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == list.size - 1) {
            VIEW_TYPE_ADD
        } else {
            VIEW_TYPE_ITEM
        }
    }
    fun setMediationList(items : List<MeditationElement>){
        list = items.toMutableList()
        notifyDataSetChanged()
    }
//    fun deleteItem(element : MeditationElement, position: Int){
//        list.remove(element)
//        notifyItemRemoved(position)
//    }
//    fun addItem(item: MeditationElement) {
//        _meditationList.add(item)
//        notifyItemInserted(_meditationList.size + 1)
//    }

    override fun getItemCount(): Int {
        return list.size
    }
    
    companion object{
        private val VIEW_TYPE_ITEM = 0
        private val VIEW_TYPE_ADD = 1
    }
}

