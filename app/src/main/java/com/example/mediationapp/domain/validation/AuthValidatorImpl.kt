package com.example.mediationapp.domain.validation


class AuthValidatorImpl : AuthValidator {

    override fun isEmailValid(email: String): Boolean {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    override fun isPasswordValid(password: String): Boolean {
        return password.length < 5
    }
}