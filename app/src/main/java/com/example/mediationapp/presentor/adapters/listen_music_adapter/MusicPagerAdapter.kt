package com.example.mediationapp.presentor.adapters.listen_music_adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mediationapp.databinding.MusicRvItemsBinding


class MusicPagerAdapter(val data: List<List<String>>) :
    RecyclerView.Adapter<MusicPagerAdapter.MusicPagerViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicPagerViewHolder {
        val binding =
            MusicRvItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MusicPagerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MusicPagerViewHolder, position: Int) {
        val binding = holder.binding
        val layoutManager = GridLayoutManager(holder.itemView.context, 3)
        binding.musicRecyclerView.adapter = MusicRecyclerViewAdapter(data[position])
        binding.musicRecyclerView.layoutManager = layoutManager

    }

    override fun getItemCount(): Int {
        return data.size
    }


    inner class MusicPagerViewHolder(val binding: MusicRvItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }
}

