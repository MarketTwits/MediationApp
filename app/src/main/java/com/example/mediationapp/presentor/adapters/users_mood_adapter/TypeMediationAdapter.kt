package com.example.mediationapp.presentor.adapters.users_mood_adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.mediationapp.databinding.ItemAddMoodUsersBinding
import com.example.mediationapp.databinding.ItemMoodUsersBinding
import com.example.mediationapp.domain.model.MeditationElement

class TypeMediationAdapter(
    ): androidx.recyclerview.widget.ListAdapter<MeditationElement, TypeMediationViewHolder>(TypeMediationDiffCallback()) {

    var onMeditationClickListener : ((MeditationElement) -> Unit)? = null
    var onMediationLongClickListener : ((MeditationElement) -> Unit)? = null
    var onAddClickListener : ((MeditationElement) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TypeMediationViewHolder {
        return when (viewType) {
            VIEW_TYPE_ITEM -> {
                val binding: ItemMoodUsersBinding =  ItemMoodUsersBinding.inflate(LayoutInflater.from(parent.context), parent, false )
                TypeMediationViewHolder(binding)
            }
            VIEW_TYPE_ADD -> {
                val binding: ItemAddMoodUsersBinding = ItemAddMoodUsersBinding.inflate(LayoutInflater.from(parent.context), parent, false )
                TypeMediationViewHolder(binding)
            }
            else -> throw IllegalArgumentException()
        }
    }
    override fun onBindViewHolder(holder: TypeMediationViewHolder, position: Int) {
        val element = getItem(position)

        when (val binding = holder.binding) {
            is ItemMoodUsersBinding -> {
                binding.tvItemTime.text = element.timeAdded
                Glide.with(holder.itemView).load(element.imageUrl).into(binding.imItemImageMood)

                binding.root.setOnLongClickListener {
                    onMediationLongClickListener?.invoke(element)
                    true
                }
                binding.root.setOnClickListener {
                    onMeditationClickListener?.invoke(element)
                }
            }
            is ItemAddMoodUsersBinding ->{
                binding.root.setOnClickListener {
                    onAddClickListener?.invoke(element)
                }
            }
        }
    }
    override fun getItemViewType(position: Int): Int {
        val item = currentList
        return if (position == item.size - 1) {
            VIEW_TYPE_ADD
        } else {
            VIEW_TYPE_ITEM
        }
    }
    override fun getItemCount(): Int {
        return currentList.size
    }
    
    companion object{
        private const val VIEW_TYPE_ITEM = 0
        private const val VIEW_TYPE_ADD = 1
    }
}

