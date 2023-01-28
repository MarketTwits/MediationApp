package com.example.mediationapp.domain.use_case.validation

data class LoginState (val email : String = "",
                       val emailError: String? = null,
                       val password: String = "",
                       val passwordError: String? = null,
                       )