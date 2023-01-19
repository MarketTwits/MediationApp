package com.example.mediationapp.presentor.screens.user

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mediationapp.domain.model.MeditationElement
import com.example.mediationapp.data.repository.MoodRepository
import com.example.mediationapp.data.repository.UserRepository
import com.example.mediationapp.domain.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class UserProfileViewModel : ViewModel() {

    private val moodRepository = MoodRepository()
    private val userRepository = UserRepository()
    private val auth = FirebaseAuth.getInstance().currentUser?.uid

    val userLiveData: MutableLiveData<User?> = MutableLiveData()
    val list = MutableLiveData<List<MeditationElement>>()

    fun getList() {
        moodRepository.loadDataMoods()
        viewModelScope.launch {
            moodRepository.sharedList
                .catch { e ->
                    if (e !is IOException) throw e // rethrow all but IOException
                    Log.e("UserProfileViewModel", e.toString())
                }
                .collect {
                    list.postValue(it)
                }
        }
    }
    fun loadUserImage(imageUri : Uri){
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.addUserImage(imageUri)
        }
    }

    fun addItem() {
        viewModelScope.launch {
            moodRepository.addItem(createItem())
        }
    }

    fun deleteItem(item: MeditationElement) {
        viewModelScope.launch {
            moodRepository.deleteItem(item)
        }
    }

    suspend fun getUserInfo() {
        userRepository.loadUserInfo()
        viewModelScope.launch {
            userRepository.sharedList.collectLatest {
                userLiveData.postValue(it)
            }
        }
    }

    private fun createItem(): MeditationElement {
        val id = Random().nextInt()
        val userId = auth
        val sdf = SimpleDateFormat("hh:mm")
        val currentDate = sdf.format(Date())
        val newItem = MeditationElement(id, userId, "https://example.com/image.jpg", currentDate)
        return newItem
    }
}