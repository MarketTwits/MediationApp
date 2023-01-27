package com.example.mediationapp.domain.validation

data class RegistrationResult(
    val result: Boolean? = null,
    val message: String = "",
    val messageError: String = "",
)