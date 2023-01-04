package com.example.mediationapp.screens.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mediationapp.model.MeditationElement
import com.example.mediationapp.repository.MoodRepository
import com.example.mediationapp.view_models.Event
import java.text.SimpleDateFormat
import java.util.*

class UserProfileViewModel : ViewModel() {

    private val repository = MoodRepository()
    private val actionShowToast = MutableLiveData<Event<String>>()

    val list: LiveData<MutableList<MeditationElement>> = repository.getList()

    fun addItem(item: MeditationElement) {
        actionShowToast.value = Event("Item clicked")
        repository.addItem(item)
    }

    fun deleteItem(item: MeditationElement) {
        repository.deleteItem(item)
    }

    fun createItem() : MeditationElement {
        val id = Random().nextInt()
        val sdf = SimpleDateFormat("hh:mm")
        val currentDate = sdf.format(Date())
        val newItem = MeditationElement(id, "https://example.com/image.jpg", currentDate)
        return newItem
    }

}