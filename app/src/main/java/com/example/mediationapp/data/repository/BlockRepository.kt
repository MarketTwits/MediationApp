package com.example.mediationapp.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mediationapp.domain.model.BlockElement
import com.example.mediationapp.domain.model.MeditationElement
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class BlockRepository {

    val sharedList = MutableSharedFlow<List<BlockElement>>()

    fun addItem(item: BlockElement) {
        val auth = FirebaseAuth.getInstance().currentUser?.uid
        val blockListDb = FirebaseDatabase.getInstance()
            .getReference("Blocks").child("ListOfBlocks")

        blockListDb.child("${item.id}") //mood item
            .setValue(item)
            .addOnSuccessListener {
                Log.d("BlockRepo", "item added success")
            }
            .addOnFailureListener { exeption ->
                Log.d("BlockRepo", "item added failed: ${exeption.message}")
            }
    }

    fun loadBlockItems() {
        val list = arrayListOf<BlockElement>()
        val blockListDb = FirebaseDatabase.getInstance()
            .getReference("Blocks") //user table
            .child("ListOfBlocks")     //user id

        blockListDb.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val scope = CoroutineScope(Dispatchers.Main)
                // Get the data from the snapshot
                scope.launch {
                    try {
                        for (blockSnapshot in snapshot.children) {
                            val item = blockSnapshot.getValue(BlockElement::class.java)
                            if (item != null) {
                                list.add(item)
                            }
                        }
                        sharedList.emit(list)
                    } catch (e: Exception) {
                        Log.e(
                            "MOOD_REPO",
                            "Error loading list ---> ${e.message}" + e.message.toString()
                        )
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