package com.example.mediationapp.presentor.screens.registration

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope

import com.example.mediationapp.databinding.FragmentRegistrationBinding
import com.example.mediationapp.domain.validation.RegisterError
import com.example.mediationapp.domain.validation.RegisterSuccess

import com.example.mediationapp.domain.validation.RegistrationEvent
import com.example.mediationapp.presentor.screens.main.MainActivity
import com.example.mediationapp.presentor.screens.main.MainViewModel
import com.example.mediationapp.presentor.ui_events.fragmentToast
import com.example.mediationapp.presentor.ui_events.openCurrentActivity
import com.example.mediationapp.presentor.view_models.AuthViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.flow.collect
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
        setupListeners()

        lifecycleScope.launch {
            viewModel.dataResult.collect{
                Log.e("FlowTag", "fragment--> $it")
                when (it) {
                    is RegisterSuccess -> {
                        openCurrentActivity(requireContext(), MainActivity::class.java)
                    }
                    is RegisterError -> {
                        fragmentToast(it.result.toString())
                    }
                }
            }
        }
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
        validation(email, password, name, age)
        auth()
        //viewModel.signUpUser(email, password, name, age, requireContext())


    }
    private fun validation(email : String, password : String, name: String, age : String){

        viewModel.submitData(email, password, name, age)
        viewModel.state.observe(viewLifecycleOwner){
            if (it.emailError != null) {
                binding.textInputLayoutEmail.error = it.emailError
            }else{
                binding.textInputLayoutEmail.error = null
            }
            if(it.passwordError != null){
                binding.textInputLayoutPassword.error = it.passwordError
            }else{
                binding.textInputLayoutPassword.error = null
            }
            if(it.nameError != null){
                binding.textInputLayoutName.error = it.nameError
            }else{
                binding.textInputLayoutName.error = null
            }
            if(it.ageError != null){
                binding.textInputLayoutAge.error = it.ageError
            }else{
                binding.textInputLayoutAge.error = null
            }
        }
    }
    private fun auth(){
        lifecycleScope.launch {
            viewModel.validationEvents.collect { event ->
                when (event) {
                    RegistrationViewModel.ValidationEvent.Success -> {
                        notifyResultUpdate()
                    }

                }
            }
        }
    }
    private fun notifyResultUpdate(){
        val email = binding.edEmail.text.toString()
        val password = binding.edPassword.text?.trim().toString()
        val name = binding.edName.text?.trim().toString()
        val age = binding.edAge.text?.trim().toString()


        viewModel.signUpUser(email, password, name, age)
//        viewModel.registrationResult.observe(viewLifecycleOwner){
//            when (it) {
//                is RegisterSuccess -> {
//                    openCurrentActivity(requireContext(), MainActivity::class.java)
//                }
//                is RegisterError -> {
//                    fragmentToast(it.result.toString())
//                }
//            }
//        }
    }
}