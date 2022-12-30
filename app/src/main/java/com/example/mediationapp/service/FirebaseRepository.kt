package com.example.mediationapp.service

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.example.mediationapp.MainActivity
import com.example.mediationapp.screens.welcome.EntryActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class FirebaseRepository {
    private val auth = FirebaseAuth.getInstance()

    fun createUser(email : String, password : String, name : String, age : String, context: Context){
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful){
                    //---->
                    //launch activity here
                    openCurrentActivity(context, MainActivity::class.java)
                    //---->
                    Toast.makeText(context, "Ok", Toast.LENGTH_SHORT).show()

                    //Add user in database
                    updateUserInfo(name, email, context)
                }else{
                    Toast.makeText(context, "Error ${it.exception}", Toast.LENGTH_SHORT).show()
                }
            }
        //updateUserInfo(name,email,context)
    }
    fun signInUser(email: String, password: String, context: Context){
        auth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener {
                if (it.isSuccessful){
                    Toast.makeText(context, "Ok", Toast.LENGTH_SHORT).show()
                    //---->
                    //launch activity here
                       openCurrentActivity(context, MainActivity::class.java)
                    //---->
                    //
                }else{
                    Toast.makeText(context, "Error ${it.exception}", Toast.LENGTH_SHORT).show()
                    Log.d("FirebaseRepository", "Exception: ${it.exception}")
                }
            }
    }
    private fun updateUserInfo(name: String, email: String, context: Context){
        val timeStamp = System.currentTimeMillis()
        val uid = auth.uid
        val hashMap : HashMap<String, Any?> = HashMap()
        hashMap["uid"] = uid
        hashMap["name"] = name
        hashMap["email"] = email
        hashMap["image"] = "image"

        val ref = FirebaseDatabase.getInstance().getReference("Users")

        if (uid != null) {
            ref.child(uid)
                .setValue(hashMap)
                .addOnCompleteListener {
                    Toast.makeText(context, "Account created", Toast.LENGTH_SHORT).show()
                    //---->
                    //context.startActivity(Intent())
                    //launch activity here
                    //---->
                }
                .addOnFailureListener{
                    Toast.makeText(context, "Error ${it.message}", Toast.LENGTH_SHORT).show()
                    Log.d("FirebaseRepository", "Exception: ${it.message}")
                }
        }

    }
    private fun openCurrentActivity(context: Context, activity: Class<MainActivity>){
        val intent = Intent(context, activity)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }

    fun logOut(){
        auth.signOut()
    }
    companion object{
        const val USER_KEY = "USER_KEY"
    }
}