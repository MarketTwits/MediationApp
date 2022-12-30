package com.example.mediationapp.adapters

import androidx.recyclerview.widget.DiffUtil
import com.example.mediationapp.model.MeditationElement

class MoodItemDiffCallback : DiffUtil.ItemCallback<MeditationElement>() {
    override fun areItemsTheSame(oldItem: MeditationElement, newItem: MeditationElement): Boolean {
        return oldItem.id == newItem.id
    }
    override fun areContentsTheSame(oldItem: MeditationElement, newItem: MeditationElement): Boolean {
        return oldItem == newItem
    }
}