package com.example.mediationapp.presentor.screens.listen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mediationapp.data.repository.ListeningRepository
import com.example.mediationapp.data.repository.UserRepository
import com.example.mediationapp.domain.model.MeditationMusic
import com.example.mediationapp.domain.model.User
import kotlinx.coroutines.launch

class ListeningViewModel : ViewModel() {

    private val userRepository = UserRepository()
    private val listeningRepository = ListeningRepository()

    val userLiveData: MutableLiveData<User?> = MutableLiveData()
    val soundsList : MutableLiveData<List<MeditationMusic>> = MutableLiveData()

    suspend fun getUserInfo() {
        userRepository.loadUserInfo()
        viewModelScope.launch {
            userRepository.sharedList.collect {
                userLiveData.value = it
            }
        }
    }
    suspend fun getMeditationMusic()
    {
        listeningRepository.loadMeditationSounds()
        viewModelScope.launch {
            listeningRepository.sharedList.collect{
                soundsList.value = it
            }
        }
    }
}