package com.example.mediationapp.presentor.screens.main


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.mediationapp.R
import com.example.mediationapp.databinding.ActivityMainBinding



class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bottomNavigationView = binding.navBar //NavigationMenu
        val navController = findNavController(R.id.nav_host_fragment_main)
        bottomNavigationView.setupWithNavController(navController)

    }

}