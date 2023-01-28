package com.example.mediationapp.domain.use_case.validation

class ValidateAge {

    fun execute(name: String): ValidationResult {
        if (name.isEmpty()) {
            return ValidationResult(
                successful = false,
                errorMessage = "The age should be correctly"
            )
        }
        if (name.length > 150) {
            return ValidationResult(
                successful = false,
                errorMessage = "The age should be correctly"
            )
        }
        return ValidationResult(successful = true)
    }

}