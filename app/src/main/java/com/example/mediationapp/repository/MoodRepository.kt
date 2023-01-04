package com.example.mediationapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mediationapp.model.MeditationElement
import java.text.SimpleDateFormat
import java.util.*

class MoodRepository() {

    private val list = MutableLiveData<MutableList<MeditationElement>>()

    // It's fake list from api or database
    val currentList = list.value?.toMutableList() ?: mutableListOf()

    init {
        val testListMeditation : MutableList<MeditationElement> =
            listOf(addItem(), addItem(), addItem()).toMutableList()
        list.value = testListMeditation
    }

    fun getList(): LiveData<MutableList<MeditationElement>> {
        return list
    }

    fun addItem(item: MeditationElement) {
        currentList.add(item)
        list.value = currentList
    }
    fun deleteItem(item: MeditationElement){
        currentList.remove(item)
        list.value = currentList
    }

    fun removeItem(item: MeditationElement) {
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