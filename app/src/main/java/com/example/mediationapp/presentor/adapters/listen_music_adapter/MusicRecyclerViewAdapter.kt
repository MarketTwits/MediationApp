package com.example.mediationapp.presentor.adapters.listen_music_adapter

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mediationapp.R
import com.example.mediationapp.databinding.ItemListenMusicSlideBinding
import com.example.mediationapp.domain.model.MeditationMusic


class MusicRecyclerViewAdapter : ListAdapter<MeditationMusic,
        MusicRecyclerViewAdapter.MusicRecyclerViewViewHolder>(MusicItemDiffCallback()) {

    inner class MusicRecyclerViewViewHolder(val binding: ItemListenMusicSlideBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

    var onMusicClickListener: ((MeditationMusic) -> Unit)? = null

    private var selectedPosition = -1


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicRecyclerViewViewHolder {
        val binding = ItemListenMusicSlideBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return MusicRecyclerViewViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: MusicRecyclerViewViewHolder,
        @SuppressLint("RecyclerView") position: Int,
    ) {
        val context = (holder.binding.root.context)
        val binding = holder.binding
        val element = getItem(position)
        val selected = position == selectedPosition
        binding.tvMusicTitle.text = element.songName

        if (selected) {
            binding.imMusicItemBackground.backgroundTintList =
                ColorStateList.valueOf(ContextCompat.getColor(context, R.color.black))
        } else {
            binding.imMusicItemBackground.backgroundTintList =
                ColorStateList.valueOf(ContextCompat.getColor(context, R.color.white))
        }
        holder.itemView.setOnClickListener {

            val oldSelectedPosition = selectedPosition
            selectedPosition = position
            onMusicClickListener?.invoke(element)
            if (oldSelectedPosition != -1) {
                notifyItemChanged(oldSelectedPosition)
            }
            notifyItemChanged(selectedPosition)
        }
    }
}
