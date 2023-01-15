package com.example.mediationapp.presentor.adapters.feelings_adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.mediationapp.domain.model.FeelingsElement

class FeelingsItemDiffCallback : DiffUtil.ItemCallback<FeelingsElement>() {
    override fun areItemsTheSame(oldItem: FeelingsElement, newItem: FeelingsElement): Boolean {
        return oldItem.id == newItem.id
    }
    override fun areContentsTheSame(oldItem: FeelingsElement, newItem: FeelingsElement): Boolean {
        return oldItem == newItem
    }
}