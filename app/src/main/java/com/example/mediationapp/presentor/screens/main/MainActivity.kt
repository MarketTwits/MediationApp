package com.example.mediationapp.presentor.screens.main


import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.mediationapp.R
import com.example.mediationapp.databinding.ActivityMainBinding
import com.example.mediationapp.domain.model.User
import com.example.mediationapp.presentor.screens.welcome.EntryActivity
import com.example.mediationapp.presentor.ui_events.fragmentToast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val bottomNavigationView = binding.navBar
        val navController = findNavController(R.id.nav_host_fragment_main)
        bottomNavigationView.setupWithNavController(navController)
    }

}