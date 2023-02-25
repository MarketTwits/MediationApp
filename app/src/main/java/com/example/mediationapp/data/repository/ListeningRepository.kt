package com.example.mediationapp.data.repository

import android.util.Log
import com.example.mediationapp.domain.model.FeelingsElement
import com.example.mediationapp.domain.model.MeditationMusic
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class ListeningRepository {

    val sharedList = MutableSharedFlow<List<MeditationMusic>>()

    fun loadMeditationSounds() {
        val list = arrayListOf<MeditationMusic>()
        val blockListDb = FirebaseDatabase.getInstance()
            .getReference("Music")
            .child("ListOfMusic")

        blockListDb.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val scope = CoroutineScope(Dispatchers.Main)
                scope.launch {
                    try {
                        for (blockSnapshot in snapshot.children) {
                            val item = blockSnapshot.getValue(MeditationMusic::class.java)
                            if (item != null) {
                                list.add(item)
                            }
                        }
                        sharedList.emit(list)
                    } catch (e: Exception) {
                        Log.e(
                            "FeelingsRepo",
                            "Error loading list ---> ${e.message}" + e.message.toString()
                        )
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle any errors
            }
        })
    }

}