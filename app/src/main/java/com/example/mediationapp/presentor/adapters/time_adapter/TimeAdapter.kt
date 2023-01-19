package com.example.mediationapp.presentor.adapters.time_adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mediationapp.R

class TimeAdapter : RecyclerView.Adapter<TimeAdapter.TimeViewHolder>() {

    var timeList =
        listOf("1 мин", "2 мин", "5 мин", "10 мин", "30 мин", "60 мин", "90 мин", "120 мин")

    var onSelectedTimeClickListener: ((String) -> Unit)? = null

    class TimeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val item: TextView = itemView.findViewById(R.id.tvTimeRv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.time_list_item, parent, false)
        return TimeViewHolder(view)
    }

    override fun onBindViewHolder(holder: TimeViewHolder, position: Int) {
        val item = timeList[position]
        holder.item.text = item

        holder.itemView.setOnClickListener {
            onSelectedTimeClickListener?.invoke(item)
        }
    }

    override fun getItemCount(): Int {
        return timeList.size
    }
}