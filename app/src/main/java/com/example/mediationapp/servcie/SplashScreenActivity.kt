package com.example.mediationapp.servcie

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mediationapp.screens.welcome.EntryActivity
import com.example.mediationapp.MainActivity
import com.google.firebase.auth.FirebaseAuth

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    private val auth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkUser()
        finish()
    }
    private fun checkUser(){
        val firebaseUser = auth.currentUser
        if(firebaseUser == null){
            //user not logged
            startActivity(Intent(this, EntryActivity::class.java))
        }else{
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}