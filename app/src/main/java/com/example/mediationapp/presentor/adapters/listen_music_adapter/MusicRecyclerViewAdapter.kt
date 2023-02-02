package com.example.mediationapp.presentor.adapters.listen_music_adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mediationapp.databinding.ItemListenMusicSlideBinding

class MusicRecyclerViewAdapter(private val items: List<String>) : RecyclerView.Adapter<MusicRecyclerViewAdapter.MusicRecyclerViewViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicRecyclerViewViewHolder {
        val binding = ItemListenMusicSlideBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return MusicRecyclerViewViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MusicRecyclerViewViewHolder, position: Int) {
        val binding = holder.binding
        binding.tvMusicTitle.text = items[position]
    }

    override fun getItemCount(): Int = items.size

    inner class MusicRecyclerViewViewHolder(val binding: ItemListenMusicSlideBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }
}