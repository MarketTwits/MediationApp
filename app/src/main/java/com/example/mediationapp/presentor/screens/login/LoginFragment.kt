package com.example.mediationapp.presentor.screens.login

import IdentificationError
import IdentificationSuccess
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.mediationapp.presentor.screens.main.MainActivity
import com.example.mediationapp.databinding.FragmentLoginBinding
import com.example.mediationapp.presentor.screens.registration.RegistrationViewModel
import com.example.mediationapp.presentor.ui_events.*
import kotlinx.coroutines.launch


class LoginFragment : Fragment() {


    lateinit var binding: FragmentLoginBinding
    lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        setupListeners()

        viewModel.progressEvent.observe(viewLifecycleOwner){
            when(it){
                is LoadingProgress ->{
                    binding.progressBalLogin.visibility = View.VISIBLE
                }
                is LoadingFinish ->{
                    binding.progressBalLogin.visibility = View.GONE
                }
                else -> {
                    binding.progressBalLogin.visibility = View.GONE
                }
            }
        }

    }

    private fun setupListeners() {
        binding.btSignIn.setOnClickListener {
            validation()
            authLiveEvent()
            signInObserver()
        }
    }

    private fun validation() {
        val email = binding.edEmail.text.toString()
        val password = binding.edPassword.text?.trim().toString()

        viewModel.submitData(email, password)
        viewModel.state.observe(viewLifecycleOwner) {
            if (it.emailError != null) {
                binding.textInputLayoutEmail.error = it.emailError
            } else {
                binding.textInputLayoutEmail.error = null
            }
            if (it.passwordError != null) {
                binding.textInputLayoutPassword.error = it.passwordError
            } else {
                binding.textInputLayoutPassword.error = null
            }
        }
    }
    private fun signInObserver() {
        lifecycleScope.launch {
            viewModel.validationEvents.collect { event ->
                when (event) {
                    LoginViewModel.ValidationEvent.Success -> {
                        startLogin()
                    }
                }
            }
        }
    }
    private fun startLogin(){
        val email = binding.edEmail.text.toString().trim()
        val password = binding.edPassword.text.toString().trim()

        viewModel.signInUser(email, password)
    }

    private fun authLiveEvent() {
        viewModel.getResult()
        lifecycleScope.launch {
            viewModel.authorizationResult.observe(viewLifecycleOwner) {
                when (it) {
                    is IdentificationSuccess -> {
                        openCurrentActivity(requireContext(), MainActivity::class.java)
                    }
                    is IdentificationError -> {
                        fragmentToast(it.result.message)
                    }
                    else -> fragmentToast("Unknown error")
                }
            }
        }
    }
}