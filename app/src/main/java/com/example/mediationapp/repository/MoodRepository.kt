package com.example.mediationapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mediationapp.model.MeditationElement
import java.text.SimpleDateFormat
import java.util.*

class MoodRepository() {

    private val list = MutableLiveData<List<MeditationElement>>()

    init {
        val testListMeditation : List<MeditationElement> = listOf(addItem(), addItem(), addItem())
        list.value = testListMeditation
    }

    fun getList(): LiveData<List<MeditationElement>> {
        return list
    }

    fun addItem(item: MeditationElement) {
        val currentList = list.value?.toMutableList() ?: mutableListOf()
        currentList.add(item)
        list.value = currentList
    }

    fun removeItem(item: MeditationElement) {
        val currentList = list.value?.toMutableList() ?: mutableListOf()
        currentList.remove(item)
        list.value = currentList
    }
    private fun addItem() : MeditationElement {
        val id = Random().nextInt()
        val sdf = SimpleDateFormat("hh:mm")
        val currentDate = sdf.format(Date())
        val newItem = MeditationElement(id, "https://example.com/image.jpg", currentDate)
        return newItem
    }

}