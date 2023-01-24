package com.example.mediationapp.presentor.screens.login

import android.content.Intent
import android.os.Bundle
import android.os.PatternMatcher
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mediationapp.R
import com.example.mediationapp.presentor.screens.main.MainActivity
import com.example.mediationapp.databinding.FragmentLoginBinding
import com.example.mediationapp.presentor.ui_events.fragmentToast
import com.example.mediationapp.presentor.view_models.AuthViewModel
import com.google.firebase.auth.FirebaseAuth
import java.util.regex.Pattern


class LoginFragment : Fragment() {


    lateinit var binding: FragmentLoginBinding
    lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
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
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        setupListeners()
    }

    private fun setupListeners() {
        binding.btSignIn.setOnClickListener {
            signInUser()
            validation()
        }
    }

    private fun signInUser() {
        viewModel.signInUser(
            binding.edEmail.text.toString(),
            binding.edPassword.text.toString(),
            requireContext()
        )
    }

    private fun validation() {
        val email = binding.edEmail.text.toString()
        val password = binding.edPassword.text?.trim().toString()
        viewModel.validateEntry(email, password)
        viewModel.isEmailValid.observe(viewLifecycleOwner) {
            if (!it) {
                binding.textInputLayoutEmail.error = getString(R.string.enter_correct_email)
            } else {
                binding.textInputLayoutEmail.error = null
            }
        }
        viewModel.isPasswordValid.observe(viewLifecycleOwner) {
            if (it) {
                binding.textInputLayoutPassword.error = getString(R.string.error_short_password)
            } else {
                binding.textInputLayoutPassword.error = null
            }
        }
    }

    private fun checkUser() {
        val firebaseUser = FirebaseAuth.getInstance().currentUser
        if (firebaseUser == null) {
            //user not logged
            fragmentToast(null, R.string.you_need_sign_in)
        } else {
            startActivity(Intent(requireActivity(), MainActivity::class.java))
        }
    }
}