package com.example.mediationapp.presentor.view_models

import android.content.Context

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.example.mediationapp.data.firebase.FirebaseRepository
import kotlinx.coroutines.launch


class AuthViewModel : ViewModel() {

    fun signInUser(email: String, password: String, context: Context) {
        if (validateLoginInputString(email, password)) {
            viewModelScope.launch {
                FirebaseRepository().signInUser(email, password, context)
            }
        }
    }

    fun signUpUser(email: String, password: String, name: String, age: String, context: Context) {
        if (validateRegistrationInputString(email, password, name, age)) {
            viewModelScope.launch {
                FirebaseRepository().createUser(email, password, name, age, context)
            }
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

    private fun validateLoginInputString(email: String, password: String): Boolean {
        return !(email.isEmpty() || password.isEmpty())
    }
}