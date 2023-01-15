package com.example.mediationapp.presentor.adapters.block_adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mediationapp.databinding.ItemMainBlockBinding
import com.example.mediationapp.domain.model.BlockElement

//class BlockItemAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
class BlockItemAdapter : ListAdapter<BlockElement,
        BlockItemAdapter.BlockViewHolder>(BlockItemDiffCallback()) {

    inner class BlockViewHolder(val binding: ItemMainBlockBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }
    var onBlockClickListener : ((BlockElement) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BlockViewHolder {

        val binding = ItemMainBlockBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return BlockViewHolder(binding)
    }
    override fun onBindViewHolder(holder: BlockViewHolder, position: Int) {
        val binding = holder.binding
        val element = getItem(position)
        binding.btDetailed.setOnClickListener {
            onBlockClickListener?.invoke(element)
        }
        binding.tvBlockTitle.text = element.title
        binding.tvBlockDescription.text = element.description
        Glide.with(binding.imBlock).load(element.imageUrl).into(binding.imBlock)
    }

    override fun getItemCount(): Int {
        return super.getItemCount()
    }
}