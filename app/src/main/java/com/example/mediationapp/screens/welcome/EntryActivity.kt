package com.example.mediationapp.screens.welcome

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.mediationapp.R
import com.example.mediationapp.databinding.ActivityEntryBinding


class EntryActivity : AppCompatActivity() {
    lateinit var binding: ActivityEntryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEntryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        fragmentNavigation(R.id.entry_fragment_container, WelcomeFragment())




    }
    fun fragmentNavigation(fragmentCotnainer : Int, fragment : Fragment ){
        supportFragmentManager.beginTransaction()
            .replace(fragmentCotnainer, fragment)
            .commit()
    }
}