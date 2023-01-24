package com.example.mediationapp.presentor.screens.registration

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mediationapp.data.firebase.FirebaseRepository
import com.example.mediationapp.domain.validation.RegistrationValidationImpl
import kotlinx.coroutines.launch

class RegistrationViewModel : ViewModel() {

    private val _isEmailValid = MutableLiveData<Boolean>()
    val isEmailValid: LiveData<Boolean> = _isEmailValid

    private val _isPasswordValid = MutableLiveData<Boolean>()
    val isPasswordValid : LiveData<Boolean> = _isPasswordValid

    private val _isAgeValid = MutableLiveData<Boolean>()
    val isAgeValid : LiveData<Boolean> = _isAgeValid

    private val _isValid = MutableLiveData<Boolean>()
    val isValid : LiveData<Boolean> = _isValid





    fun signUpUser(email: String, password: String, name: String, age: String, context: Context) {
        if (validateRegistrationInputString(email, password, name, age)) {
            viewModelScope.launch {
                FirebaseRepository().createUser(email, password, name, age, context)
            }
        }
    }

    fun validateRegistration(email: String, password: String, age:String) {
        val validator = RegistrationValidationImpl()
        viewModelScope.launch {
            _isEmailValid.value = validator.isEmailValid(email)
        }
        viewModelScope.launch {
            _isPasswordValid.value = validator.isPasswordValid(password)
        }
        viewModelScope.launch {
            _isAgeValid.value = validator.isAgeValid(safeToInt(age))
        }
    }
    private fun safeToInt(string: String): Int {
        if (string.isNotBlank()) {
            return string.toInt()
        } else {
            return 0
        }
    }

    private fun validateRegistrationInputString(
        email: String,
        password: String,
        name: String,
        age: String,
    ): Boolean {
        return !(email.isEmpty() || password.isEmpty() || name.isEmpty() || age.isEmpty())
    }

}