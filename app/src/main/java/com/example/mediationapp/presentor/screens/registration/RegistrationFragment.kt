package com.example.mediationapp.presentor.screens.registration

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.mediationapp.R
import com.example.mediationapp.databinding.FragmentRegistrationBinding
import com.example.mediationapp.presentor.view_models.AuthViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase


class RegistrationFragment : Fragment() {


    // TODO: Rename and change types of parameters
    lateinit var binding: FragmentRegistrationBinding
    lateinit var viewModel: RegistrationViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[RegistrationViewModel::class.java]
        setupListeners()
    }

    private fun setupListeners() {
        binding.btRegistration.setOnClickListener {
            signUpUser()
        }
    }

    private fun signUpUser() {
        val email = binding.edEmail.text.toString().trim()
        val password = binding.edPassword.text.toString().trim()
        val name = binding.edName.text.toString().trim()
        val age = binding.edAge.text.toString().trim()
        validation()
        viewModel.signUpUser(email, password, name, age, requireContext())

    }

    private fun validation(){
        val email = binding.edEmail.text.toString()
        val password = binding.edPassword.text?.trim().toString()
        val age = binding.edAge.text?.trim().toString()

        viewModel.validateRegistration(email, password, age)
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
            viewModel.isAgeValid.observe(viewLifecycleOwner) {
                if (!it) {
                    binding.textInputLayoutAge.error = getString(R.string.enter_correct_age)
                } else {
                    binding.textInputLayoutAge.error = null
                }
            }
    }

}