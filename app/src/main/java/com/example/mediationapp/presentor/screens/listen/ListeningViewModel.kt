package com.example.mediationapp.presentor.screens.listen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mediationapp.data.repository.UserRepository
import com.example.mediationapp.domain.model.User
import kotlinx.coroutines.launch

class ListeningViewModel : ViewModel() {

    private val userRepository = UserRepository()
    val userLiveData: MutableLiveData<User?> = MutableLiveData()


    suspend fun getUserInfo() {
        userRepository.loadUserInfo()
        viewModelScope.launch {
            userRepository.sharedList.collect {
                userLiveData.value = it
            }
        }
    }

}