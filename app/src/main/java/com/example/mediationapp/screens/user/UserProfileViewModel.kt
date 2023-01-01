package com.example.mediationapp.screens.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.mediationapp.model.MeditationElement
import com.example.mediationapp.repository.MoodRepository
import java.text.SimpleDateFormat
import java.util.*

class UserProfileViewModel : ViewModel() {

    private val repository = MoodRepository()

    val list: LiveData<List<MeditationElement>> = repository.getList()

    fun getItems() : LiveData<List<MeditationElement>>{
        return repository.getList()
    }

    fun addItem(item: MeditationElement) {
        repository.addItem(item)
    }

//    fun deelteItem(item: MeditationElement) {
//        repository.addItem(item)
//    }

    fun removeItem(item: MeditationElement) {
        repository.removeItem(item)
    }

    fun createItem() : MeditationElement {
        val id = Random().nextInt()
        val sdf = SimpleDateFormat("hh:mm")
        val currentDate = sdf.format(Date())
        val newItem = MeditationElement(id, "https://example.com/image.jpg", currentDate)
        return newItem
    }

}