package com.example.mediationapp.domain.validation

class RegistrationValidationImpl : RegistrationValidation {
    override fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    override fun isPasswordValid(password: String): Boolean {
        return password.length < 5
    }

    override fun isAgeValid(age: Int): Boolean {
        if (age < 150){
            if(age > 0){
                return true
            }
        }
        return false
    }

}