package com.example.mediationapp.data.firebase

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mediationapp.domain.model.MeditationElement
import com.example.mediationapp.presentor.screens.main.MainActivity
import com.example.mediationapp.domain.model.User
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class FirebaseRepository {
    private val auth = FirebaseAuth.getInstance()
    private val scope = CoroutineScope(Dispatchers.Main)
    val registrationResult =  MutableSharedFlow<Task<Void>>()



    fun createUser(email: String, password: String, name: String, age: String,) {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        createUserData(name, email, age)
                    }
                }
                .addOnFailureListener {

                }
            //updateUserInfo(name,email,context)
        }


    fun signInUser(email: String, password: String, context: Context) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(context, "Ok", Toast.LENGTH_SHORT).show()
                    //---->
                    //launch activity here
                    openCurrentActivity(context, MainActivity::class.java)
                    //---->
                    //
                } else {
                    Toast.makeText(context, "Error ${it.exception}", Toast.LENGTH_SHORT).show()
                    Log.d("FirebaseRepository", "Exception: ${it.exception}")
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
                taskMessage(it)
            }
            .addOnFailureListener {

            }
    }

    private fun openCurrentActivity(context: Context, activity: Class<MainActivity>) {
        val intent = Intent(context, activity)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }

    companion object {
        const val USER_KEY = "USER_KEY"
    }
    private fun taskMessage(task : Task<Void> ){
        scope.launch {
            if(task.isSuccessful){
                registrationResult.emit(task)
            }
            else{
                registrationResult.emit(task)
            }
        }
    }
}