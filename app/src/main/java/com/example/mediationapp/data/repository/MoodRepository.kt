package com.example.mediationapp.data.repository

import android.provider.ContactsContract
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mediationapp.domain.model.MeditationElement
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firestore.v1.ListenResponse
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flow
import java.text.SimpleDateFormat
import java.util.*
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime


class MoodRepository() {

    val sharedList = MutableSharedFlow<List<MeditationElement>>()
    private val auth = FirebaseAuth.getInstance().currentUser?.uid

    fun addItem(item: MeditationElement) {
        val auth = FirebaseAuth.getInstance().currentUser?.uid
        val moodListDb = FirebaseDatabase.getInstance()
            .getReference("Users") //user table
            .child(auth.toString())     //user id
            .child("MoodList") //mood list row

        moodListDb.child("${item.id}") //mood item
            .setValue(item)
            .addOnSuccessListener {
                Log.d("UserProfileViewModel", "item added success")
            }
            .addOnFailureListener { exeption ->
                Log.d("UserProfileViewModel", "item added failed: ${exeption.message}")
            }
    }

    fun deleteItem(item: MeditationElement) {
        val moodListDb = FirebaseDatabase.getInstance()
            .getReference("Users")
            .child(auth.toString())
            .child("MoodList")
            .child(item.id.toString())
        moodListDb.removeValue()
            .addOnSuccessListener {
                Log.d("UserProfileViewModel", "item deleted success")
            }
            .addOnFailureListener {
                Log.d("UserProfileViewModel", "delete error")
            }
    }

    fun loadDataMoods() {
        val moodListDb = FirebaseDatabase.getInstance()
            .getReference("Users")
            .child(auth.toString())
            .child("MoodList")
        moodListDb.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                getMeditationElements(snapshot)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    private fun getMeditationElements(snapshot: DataSnapshot) {
        val scope = CoroutineScope(Dispatchers.Main)
        val list = arrayListOf<MeditationElement>()
        scope.launch {
            try {
                for (moodSnapshot in snapshot.children) {
                    val item = moodSnapshot.getValue(MeditationElement::class.java)
                    if (item != null) {
                        list.add(item)
                    }
                }
                sharedList.emit(list)
            } catch (e: Exception) {
                Log.e("ERROR_TAG", "Error loading list ---> ${e.message}" + e.message.toString())
            }
        }
    }
}




