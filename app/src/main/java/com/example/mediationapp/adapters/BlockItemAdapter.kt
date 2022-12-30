package com.example.mediationapp.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.example.mediationapp.R
import com.example.mediationapp.databinding.ItemMainBlockBinding
import com.example.mediationapp.model.BlockElement

//class BlockItemAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
class BlockItemAdapter : ListAdapter<BlockElement,
        BlockItemAdapter.BlockViewHolder>(BlockItemDiffCallback()) {

    inner class BlockViewHolder(val binding: ItemMainBlockBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }
    private var blockList = mutableListOf<BlockElement>()

    var onBlockClickListener : ((BlockElement) -> Unit)? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BlockViewHolder {

        val binding = ItemMainBlockBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return BlockViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BlockViewHolder, position: Int) {
        val binding = holder.binding
        val element = blockList[position]

        binding.btDetailed.setOnClickListener {
            onBlockClickListener?.invoke(element)
        }

        binding.tvBlockTitle.text = element.title
        binding.tvBlockDescription.text = element.description
        Glide.with(binding.imBlock).load(element.imageUrl).into(binding.imBlock)


    }
    fun addItem(blockElement: BlockElement){
        blockList.add(blockElement)
        submitList(blockList)
        notifyItemInserted(blockList.size + 1)
    }

    override fun getItemCount(): Int {
        return super.getItemCount()
    }
}