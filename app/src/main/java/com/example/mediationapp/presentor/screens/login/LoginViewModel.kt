package com.example.mediationapp.presentor.screens.login

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mediationapp.data.firebase.FirebaseRepository
import com.example.mediationapp.domain.validation.AuthValidatorImpl
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val _isEmailValid = MutableLiveData<Boolean>()
    val isEmailValid: LiveData<Boolean> = _isEmailValid

    private val _isPasswordValid = MutableLiveData<Boolean>()

    val isPasswordValid : LiveData<Boolean> = _isPasswordValid
    fun signInUser(email: String, password: String, context: Context) {
        if (validateLoginInputString(email, password)) {
            viewModelScope.launch {
                FirebaseRepository().signInUser(email, password, context)
            }
        }
    }
    fun validateEntry(email: String, password: String) {
        val validator = AuthValidatorImpl()
        viewModelScope.launch {
            _isEmailValid.value = validator.isEmailValid(email)
        }
        viewModelScope.launch {
            _isPasswordValid.value = validator.isPasswordValid(password)
        }
    }

    private fun validateLoginInputString(email: String, password: String): Boolean {
        return !(email.isEmpty() || password.isEmpty())
    }

}