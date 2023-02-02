package com.example.mediationapp.data.firebase

import ResponseError
import ResponseEvent
import ResponseSuccess
import android.util.Log
import com.example.mediationapp.domain.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class RegistrationRepository {

    private val auth = FirebaseAuth.getInstance()
    private val scope = CoroutineScope(Dispatchers.Main)
    val registrationResult = MutableSharedFlow<ResponseEvent>()

    fun createUser(email: String, password: String, name: String, age: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                createUserData(name, email, age)
            }
            .addOnFailureListener {
                scope.launch {
                    registrationResult.emit(ResponseError(it))
                    Log.e("FlowTag", "repository-->$it")
                }
            }
    }
    private fun createUserData(name: String, email: String, age: String) {
        val uid = auth.uid.toString()
        val user = User(
            uid = uid,
            email = email,
            name = name,
            age = age,
        )
        FirebaseDatabase.getInstance()
            .getReference("Users")
            .child(uid)
            .child("UserInfo")
            .setValue(user)
            .addOnCompleteListener {
                scope.launch {
                    registrationResult.emit(ResponseSuccess(it.isComplete.toString()))
                    Log.e("FlowTag", "repository-->${it.isComplete}")
                }
            }
            .addOnFailureListener {
                scope.launch {
                    registrationResult.emit(ResponseError(it))
                }
            }
    }
}