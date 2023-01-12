package com.example.mediationapp.screens.user

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mediationapp.model.MeditationElement
import com.example.mediationapp.repository.MoodRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class UserProfileViewModel : ViewModel() {

    private val repository = MoodRepository()
    private lateinit var firebaseAuth: FirebaseAuth

    val list =  MutableLiveData<List<MeditationElement>>()

    fun getList(){
       repository.loadDataMoods()
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

    fun addItem() {
        viewModelScope.launch {
            addMoodInDatabase(createItem())
            repository.addItem(createItem())
        }
    }
    fun deleteItem(item: MeditationElement) {
        viewModelScope.launch {
            repository.deleteItem(item)
            deleteMoodInDatabase(item)
        }
    }
    fun createItem() : MeditationElement {
        val auth = FirebaseAuth.getInstance().currentUser?.uid
        val id = Random().nextInt()
        val userId = auth
        val sdf = SimpleDateFormat("hh:mm")
        val currentDate = sdf.format(Date())
        val newItem = MeditationElement(id, userId, "https://example.com/image.jpg", currentDate)
        return newItem
    }

    private fun addMoodInDatabase(item: MeditationElement){
        val ref = FirebaseDatabase.getInstance().getReference("UserMood")
        ref.child("${item.id}")
            .setValue(item)
            .addOnSuccessListener {
                Log.d("UserProfileViewModel", "item added success")
            }
            .addOnFailureListener { exeption ->
                Log.d("UserProfileViewModel", "item added failed: ${exeption.message}") }

//        val ref = FirebaseDatabase.getInstance().getReference("UserMood")
//        ref.child("${item.id}")
//            .setValue(item)
//            .addOnSuccessListener {
//                Log.d("UserProfileViewModel", "item added success")
//            }
//            .addOnFailureListener { exeption ->
//                Log.d("UserProfileViewModel", "item added failed: ${exeption.message}") }
    }
    private fun deleteMoodInDatabase(item: MeditationElement){
        val dR = FirebaseDatabase.getInstance().getReference("UserMood").child(item.id.toString())
        dR.removeValue()
            .addOnSuccessListener {
            Log.d("UserProfileViewModel", "item deleted success")
        }
            .addOnFailureListener {
                Log.d("UserProfileViewModel", "delete error")
            }
    }


}