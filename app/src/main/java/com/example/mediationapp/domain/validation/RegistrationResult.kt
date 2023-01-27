package com.example.mediationapp.domain.validation

import com.google.android.gms.tasks.Task


sealed class RegistrationEvent
data class RegisterSuccess(val result: Task<Void>) : RegistrationEvent()
data class RegisterError(val result: Exception) : RegistrationEvent()

