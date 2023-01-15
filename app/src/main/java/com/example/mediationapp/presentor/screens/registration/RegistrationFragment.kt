package com.example.mediationapp.presentor.screens.registration

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.mediationapp.databinding.FragmentRegistrationBinding
import com.example.mediationapp.presentor.view_models.AuthViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase



class RegistrationFragment : Fragment() {


    // TODO: Rename and change types of parameters
    lateinit var binding: FragmentRegistrationBinding
    lateinit var viewModel: AuthViewModel
    private val auth = FirebaseAuth.getInstance()

    private val userDbReference = FirebaseDatabase.getInstance().reference.child("Users")



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View{
        // Inflate the layout for this fragment
        binding = FragmentRegistrationBinding.inflate(inflater,container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[AuthViewModel::class.java]

        setupListeners()
    }
    private fun setupListeners(){
        binding.btRegistration.setOnClickListener {
            signUpUser()
        }
    }
    private fun signUpUser(){
        val email = binding.edEmail.text.toString().trim()
        val password = binding.edPassword.text.toString().trim()
        val name = binding.edName.text.toString().trim()
        val age =  binding.edAge.text.toString().trim()

        viewModel.signUpUser(email,password,name,age,requireContext())

    }

}