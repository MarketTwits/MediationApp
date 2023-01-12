package com.example.mediationapp.repository

import android.provider.ContactsContract
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mediationapp.model.MeditationElement
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


    //private val list = MutableLiveData<MutableList<MeditationElement>>()
    val sharedList = MutableSharedFlow<List<MeditationElement>>()

    fun addItem(item: MeditationElement) {
        //list.value?.add(item)
    }

    fun deleteItem(item: MeditationElement) {
        //list.value?.remove(item)
    }

     fun loadDataMoods()  {
            val ref = FirebaseDatabase.getInstance().getReference("UserMood")
            ref.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                 getMeditationElements(snapshot)
                }
                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
    }
    @OptIn(ExperimentalTime::class)
    private fun getMeditationElements(snapshot : DataSnapshot) {
        val authId = FirebaseAuth.getInstance().currentUser?.uid
        val scope = CoroutineScope(Dispatchers.Main)
        val list = arrayListOf<MeditationElement>()
        scope.launch {
            try {
                val time  = measureTime {
                    for (moodSnapshot in snapshot.children){
                        val moodId = moodSnapshot.child("userId").getValue(String::class.java)
                        if (authId == moodId) {
                            val item = moodSnapshot.getValue(MeditationElement::class.java)
                            if (item != null) {
                                list.add(item)
                            }
                        }
                    }
                    sharedList.emit(list)
                }
                Log.e("ERROR_TAG", time.toString())
            }catch (e : Exception){
                Log.e("ERROR_TAG", "Error loading list ---> " +  e.message.toString())
            }
        }
    }
}




