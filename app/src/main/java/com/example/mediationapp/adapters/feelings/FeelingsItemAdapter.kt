package com.example.mediationapp.adapters.feelings

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mediationapp.databinding.ItemFeelingsBlockBinding
import com.example.mediationapp.databinding.ItemMainBlockBinding
import com.example.mediationapp.model.BlockElement
import com.example.mediationapp.model.FeelingsElement

//class BlockItemAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
class FeelingsItemAdapter : ListAdapter<FeelingsElement,
        FeelingsItemAdapter.FeelingsViewHolder>(FeelingsItemDiffCallback()) {

    inner class FeelingsViewHolder(val binding: ItemFeelingsBlockBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }
    private var feelingsList = mutableListOf<FeelingsElement>()

    var onFeelingsItemClickListener : ((FeelingsElement) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeelingsViewHolder {

        val binding = ItemFeelingsBlockBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return FeelingsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FeelingsViewHolder, position: Int) {
        val binding = holder.binding
        val element = feelingsList[position]

        binding.imFeelingsItem.setOnClickListener {
            onFeelingsItemClickListener?.invoke(element)
        }
        binding.tvFeelingsTitle.text = element.title
        Glide.with(binding.imFeelingsItem).load(element.imageUrl).into(binding.imFeelingsItem)


    }
    fun addItem(feelingsElement: FeelingsElement){
        feelingsList.add(feelingsElement)
        submitList(feelingsList)
        notifyItemInserted(feelingsList.size + 1)
    }

    override fun getItemCount(): Int {
        return super.getItemCount()
    }
}