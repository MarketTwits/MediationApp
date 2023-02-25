package com.example.mediationapp.presentor.adapters.listen_music_adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.mediationapp.domain.model.MeditationMusic

class MusicItemDiffCallback : DiffUtil.ItemCallback<MeditationMusic>() {
    override fun areItemsTheSame(oldItem: MeditationMusic, newItem: MeditationMusic): Boolean {
        return oldItem.id == newItem.id
    }
    override fun areContentsTheSame(oldItem: MeditationMusic, newItem: MeditationMusic): Boolean {
        return oldItem == newItem
    }
}