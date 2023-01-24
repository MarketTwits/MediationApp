package com.example.mediationapp.domain.validation

interface RegistrationValidation {
    fun isEmailValid(email: String): Boolean
    fun isPasswordValid(password: String) : Boolean
    fun isAgeValid(age : Int) : Boolean
}