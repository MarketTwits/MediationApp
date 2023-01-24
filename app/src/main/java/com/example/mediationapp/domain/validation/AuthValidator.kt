package com.example.mediationapp.domain.validation

interface AuthValidator {
   fun isEmailValid(email: String): Boolean
   fun isPasswordValid(password: String) : Boolean
}