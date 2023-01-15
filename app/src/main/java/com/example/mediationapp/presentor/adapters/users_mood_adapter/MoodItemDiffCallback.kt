package com.example.mediationapp.presentor.adapters.users_mood_adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.mediationapp.domain.model.MeditationElement

class MoodItemDiffCallback : DiffUtil.ItemCallback<MeditationElement>() {
    override fun areItemsTheSame(oldItem: MeditationElement, newItem: MeditationElement): Boolean {
        return oldItem.id == newItem.id
    }
    override fun areContentsTheSame(oldItem: MeditationElement, newItem: MeditationElement): Boolean {
        return oldItem == newItem
    }
}