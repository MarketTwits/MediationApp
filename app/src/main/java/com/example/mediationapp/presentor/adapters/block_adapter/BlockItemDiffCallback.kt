package com.example.mediationapp.presentor.adapters.block_adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.mediationapp.domain.model.BlockElement

class BlockItemDiffCallback : DiffUtil.ItemCallback<BlockElement>() {
    override fun areItemsTheSame(oldItem: BlockElement, newItem: BlockElement): Boolean {
        return oldItem.id == newItem.id
    }
    override fun areContentsTheSame(oldItem: BlockElement, newItem: BlockElement): Boolean {
        return oldItem == newItem
    }
}