package com.example.mediationapp.data.firebase

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.example.mediationapp.presentor.screens.main.MainActivity
import com.example.mediationapp.domain.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class FirebaseRepository {
    private val auth = FirebaseAuth.getInstance()

    fun createUser(email: String, password: String, name: String, age: String, context: Context) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    //---->
                    //launch activity here
                    openCurrentActivity(context, MainActivity::class.java)
                    //---->
                    Toast.makeText(context, "Ok", Toast.LENGTH_SHORT).show()

                    //Add user in database
                    createUserData(name, email, age, context)
                } else {
                    Toast.makeText(context, "Error ${it.exception}", Toast.LENGTH_SHORT).show()
                }
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

    private fun createUserData(name: String, email: String, age: String, context: Context) {
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
                Toast.makeText(context, "Account created", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(context, "Error ${it.message}", Toast.LENGTH_SHORT).show()
                Log.d("FirebaseRepository", "Exception: ${it.message}")
            }
    }

    private fun openCurrentActivity(context: Context, activity: Class<MainActivity>) {
        val intent = Intent(context, activity)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }

    fun logOut() {
        auth.signOut()
    }

    companion object {
        const val USER_KEY = "USER_KEY"
    }
}