package com.example.mediationapp.screens.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mediationapp.model.BlockElement
import com.example.mediationapp.model.MeditationElement
import com.example.mediationapp.repository.BlockRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import java.io.IOException
import java.util.*

class MainViewModel : ViewModel() {

    val repository = BlockRepository()

    val list =  MutableLiveData<List<BlockElement>>()

    fun getList(){
        repository.loadBlockItems()
        viewModelScope.launch{
            repository.sharedList
                .catch { e ->
                    if (e !is IOException) throw e // rethrow all but IOException
                    Log.e("UserProfileViewModel", e.toString())
                }
                .collect{
                    list.postValue(it)
                }
        }
    }
    fun createBlocks(){
        val block = BlockElement(
            Random().nextInt(),
            "Title",
            "Краткое описание блока двумя строчками",
            "mainText",
            "https://pcdn.columbian.com/wp-content/uploads/2021/06/0615_fea_meditation.jpg"
        )
        repository.addItem(block)
    }

}