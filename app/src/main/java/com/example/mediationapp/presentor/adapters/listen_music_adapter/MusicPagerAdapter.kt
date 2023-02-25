package com.example.mediationapp.presentor.adapters.listen_music_adapter


import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mediationapp.databinding.MusicRvItemsBinding
import com.example.mediationapp.domain.model.MeditationMusic
import com.example.mediationapp.presentor.ui_events.divideList


class MusicPagerAdapter(val data: List<List<MeditationMusic>>) :
    RecyclerView.Adapter<MusicPagerAdapter.MusicPagerViewHolder>() {

    var onMusicClickListener: ((MeditationMusic) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicPagerViewHolder {
        val binding =
            MusicRvItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MusicPagerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MusicPagerViewHolder, position: Int) {
        val binding = holder.binding
        val layoutManager = GridLayoutManager(holder.itemView.context, 3)
        val musicRVAdapter = MusicRecyclerViewAdapter()
        binding.musicRecyclerView.adapter = musicRVAdapter
        binding.musicRecyclerView.layoutManager = layoutManager
        musicRVAdapter.submitList(data[position])

        musicRVAdapter.onMusicClickListener = {
            onMusicClickListener?.invoke(it)
        }
        Log.d("MusicRecyclerViewAdapter", "Pager ---> " + data[position].size)
    }

    override fun getItemCount(): Int {
        return data.size
    }


    inner class MusicPagerViewHolder(val binding: MusicRvItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }


}

