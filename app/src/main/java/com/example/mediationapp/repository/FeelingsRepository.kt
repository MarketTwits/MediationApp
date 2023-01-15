package com.example.mediationapp.repository

import android.util.Log
import com.example.mediationapp.model.BlockElement
import com.example.mediationapp.model.FeelingsElement
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class FeelingsRepository {

    val sharedList = MutableSharedFlow<List<FeelingsElement>>()

    fun addItem(item: FeelingsElement) {
        val blockListDb = FirebaseDatabase.getInstance()
            .getReference("Feelings").child("ListOfFeelings")

        blockListDb.child("${item.id}") //mood item
            .setValue(item)
            .addOnSuccessListener {
                Log.d("FeelingsRepo", "item added success")
            }
            .addOnFailureListener { exeption ->
                Log.d("FeelingsRepo", "item added failed: ${exeption.message}") }
    }
    fun loadFeelingItems(){
        val list = arrayListOf<FeelingsElement>()
        val blockListDb = FirebaseDatabase.getInstance()
            .getReference("Feelings") //user table
            .child("ListOfFeelings")     //user id

        blockListDb.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val scope = CoroutineScope(Dispatchers.Main)
                // Get the data from the snapshot
                scope.launch {
                    try {
                        for (blockSnapshot in snapshot.children){
                            val item = blockSnapshot.getValue(FeelingsElement::class.java)
                            if (item != null) {
                                list.add(item)
                            }
                        }
                        sharedList.emit(list)
                    }catch (e : Exception){
                        Log.e("FeelingsRepo", "Error loading list ---> ${e.message}" +  e.message.toString())
                    }
                }
                // Do something with the data
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle any errors
            }
        })
    }


}