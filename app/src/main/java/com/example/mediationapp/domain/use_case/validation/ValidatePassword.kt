package com.example.mediationapp.domain.use_case.validation

class ValidatePassword {

    fun execute(password: String): ValidationResult {
        if (password.length < 5) {
            return ValidationResult(
                successful = false,
                errorMessage = "The password needs to consist at least 5 characters"
            )
        }
        return ValidationResult(successful = true)
    }
}

