package com.example.mediationapp.domain.use_case

data class RegistrationState(
    val email : String = "",
    val emailError: String? = null,
    val password: String = "",
    val passwordError: String? = null,
    val name:  String = "",
    val nameError: String? = null,
    val age: String = "",
    val ageError: String? = null
)
