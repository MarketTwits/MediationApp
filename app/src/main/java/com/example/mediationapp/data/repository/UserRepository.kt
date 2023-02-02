package com.example.mediationapp.data.repository

import ResponseError
import ResponseEvent
import ResponseSuccess
import android.net.Uri
import android.util.Log
import com.example.mediationapp.domain.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
class UserRepository {

    val sharedList = MutableSharedFlow<User?>()
    val responseEvent = MutableSharedFlow<ResponseEvent>()
    private val auth = FirebaseAuth.getInstance().currentUser?.uid.toString()
    private val scope = CoroutineScope(Dispatchers.IO)
    private val storageRef = FirebaseStorage.getInstance().reference

    private val imagesRef = storageRef.child("users_images").child(auth).child("userImage")

    suspend fun addUserImage(imageUri: Uri) {
        val uploadTask = imagesRef.putFile(imageUri)
            .addOnCompleteListener {
                    imagesRef.downloadUrl.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val downloadUri = task.result
                        val addImageUrlInUserProfile = FirebaseDatabase.getInstance()
                            .getReference("Users") //user table
                            .child(auth)     //user id
                            .child("UserInfo")
                            .child("imageUrl") //mood item
                            .setValue(downloadUri.toString())
                        Log.e("UserRepository", "Loading image succes ---> ")
                        scope.launch {
                            responseEvent.emit(ResponseSuccess(it.toString()))
                        }
                    }
                }
            }
            .addOnFailureListener {
                Log.e("UserRepository", "Loading image failed ---> " + it.message)
                scope.launch {
                    responseEvent.emit(ResponseError(it))
                }
            }
        uploadTask.await().storage.downloadUrl.await()
    }

    suspend fun loadUserInfo() {
        var user: User? = null
        val database = FirebaseDatabase.getInstance().reference
        database.child("Users")
            .child(auth)
            .child("UserInfo").addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    scope.launch {
                        user = snapshot.getValue(User::class.java)
                        sharedList.emit(user)
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    Log.e("firebase", "Error getting data", error.toException())
                }
            })
    }
}

