package com.example.mediationapp.adapters.feelings

import androidx.recyclerview.widget.DiffUtil
import com.example.mediationapp.model.BlockElement
import com.example.mediationapp.model.FeelingsElement
import com.example.mediationapp.model.MeditationElement

class FeelingsItemDiffCallback : DiffUtil.ItemCallback<FeelingsElement>() {
    override fun areItemsTheSame(oldItem: FeelingsElement, newItem: FeelingsElement): Boolean {
        return oldItem.id == newItem.id
    }
    override fun areContentsTheSame(oldItem: FeelingsElement, newItem: FeelingsElement): Boolean {
        return oldItem == newItem
    }
}