package com.example.mediationapp.adapters.block

import androidx.recyclerview.widget.DiffUtil
import com.example.mediationapp.model.BlockElement
import com.example.mediationapp.model.MeditationElement

class BlockItemDiffCallback : DiffUtil.ItemCallback<BlockElement>() {
    override fun areItemsTheSame(oldItem: BlockElement, newItem: BlockElement): Boolean {
        return oldItem.id == newItem.id
    }
    override fun areContentsTheSame(oldItem: BlockElement, newItem: BlockElement): Boolean {
        return oldItem == newItem
    }
}