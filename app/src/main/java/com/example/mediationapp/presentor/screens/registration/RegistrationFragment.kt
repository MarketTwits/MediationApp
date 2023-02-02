package com.example.mediationapp.presentor.screens.registration

import ResponseError
import ResponseSuccess
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.mediationapp.databinding.FragmentRegistrationBinding
import com.example.mediationapp.presentor.screens.main.MainActivity
import com.example.mediationapp.presentor.ui_events.LoadingFinish
import com.example.mediationapp.presentor.ui_events.LoadingProgress
import com.example.mediationapp.presentor.ui_events.fragmentToast
import com.example.mediationapp.presentor.ui_events.openCurrentActivity
import kotlinx.coroutines.launch


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

        notifyLoadingState()
        setupListeners()
        registrationLiveEvent()
    }

    private fun setupListeners() {
        binding.btRegistration.setOnClickListener {
            signUpUser()
        }
    }

    private fun signUpUser() {
        validation()
        signUp()
    }

    private fun validation() {
        val email = binding.edEmail.text.toString().trim()
        val password = binding.edPassword.text.toString().trim()
        val name = binding.edName.text.toString().trim()
        val age = binding.edAge.text.toString().trim()
        Log.e("FlowTag", "--->  $email, $name, $age ")

        viewModel.submitData(email, password, name, age)

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
            if (it.nameError != null) {
                binding.textInputLayoutName.error = it.nameError
            } else {
                binding.textInputLayoutName.error = null
            }
            if (it.ageError != null) {
                binding.textInputLayoutAge.error = it.ageError
            } else {
                binding.textInputLayoutAge.error = null
            }
        }
    }


    private fun signUp() {
        lifecycleScope.launch {
            viewModel.validationEvents.collect { event ->
                when (event) {
                    RegistrationViewModel.ValidationEvent.Success -> {
                        startRegistration()
                    }
                }
            }
        }
    }

    fun startRegistration() {
        val email = binding.edEmail.text.toString().trim()
        val password = binding.edPassword.text.toString().trim()
        val name = binding.edName.text.toString().trim()
        val age = binding.edAge.text.toString().trim()
        viewModel.signUpUser(email, password, name, age)
    }

    private fun registrationLiveEvent() {
        viewModel.getResult()
        lifecycleScope.launch {
            viewModel.registrationResult.observe(viewLifecycleOwner) {
                when (it) {
                    is ResponseSuccess -> {
                        openCurrentActivity(requireContext(), MainActivity::class.java)
                        fragmentToast(it.result)
                    }
                    is ResponseError -> {
                        fragmentToast(it.result.message)
                    }
                    else -> fragmentToast("Unknown error")
                }
            }
        }
    }

    private fun notifyLoadingState() {
        viewModel.progressEvent.observe(viewLifecycleOwner){
            when(it){
                is LoadingProgress ->{
                    binding.progressBarRegister.visibility = View.VISIBLE
                }
                is LoadingFinish ->{
                    binding.progressBarRegister.visibility = View.GONE
                }
                else -> {
                    binding.progressBarRegister.visibility = View.GONE
                }
            }
        }
    }
}