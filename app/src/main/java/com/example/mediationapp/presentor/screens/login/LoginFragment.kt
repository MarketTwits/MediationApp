package com.example.mediationapp.presentor.screens.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mediationapp.presentor.screens.main.MainActivity
import com.example.mediationapp.databinding.FragmentLoginBinding
import com.example.mediationapp.data.firebase.FirebaseService
import com.example.mediationapp.presentor.view_models.AuthViewModel


class LoginFragment : Fragment() {



    lateinit var binding: FragmentLoginBinding
    lateinit var viewModel: AuthViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View{
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment

        //Open logined user
        binding.btProfile.setOnClickListener {
            checkUser()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[AuthViewModel::class.java]
        setupListeners()
    }
    private fun setupListeners(){
        binding.btSignIn.setOnClickListener {
            signInUser()
        }
    }
    private fun signInUser() {
        viewModel.signInUser(
            binding.edEmail.text.toString(),
            binding.edPassword.text.toString(),
            requireContext()
        )
    }
    private fun checkUser(){
        val firebaseUser = FirebaseService.auth.currentUser
        if(firebaseUser == null){
            //user not logged
            Toast.makeText(requireContext(), "You need sign in in the app ", Toast.LENGTH_SHORT).show()
        }else{
            startActivity(Intent(requireActivity(), MainActivity::class.java))
        }
    }
}