package com.example.mediationapp.domain.use_case

import com.example.mediationapp.presentor.ui_events.SafeToInt

class ValidateName {

    fun execute(name: String): ValidationResult {
        if (name.length <= 5) {
            return ValidationResult(
                successful = false,
                errorMessage = "The name needs to consist at least 5 characters"
            )
        }
        if (name.length > 50) {
            return ValidationResult(
                successful = false,
                errorMessage = "The name needs to consist maximum of 50 characters"
            )
        }
        return ValidationResult(successful = true)
    }


}