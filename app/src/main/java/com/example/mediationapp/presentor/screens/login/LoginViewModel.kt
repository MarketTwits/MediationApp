package com.example.mediationapp.presentor.screens.login

import IdentificationEvent
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mediationapp.data.firebase.AuthorizationRepository
import com.example.mediationapp.domain.use_case.validation.LoginState
import com.example.mediationapp.domain.use_case.validation.ValidateEmail
import com.example.mediationapp.domain.use_case.validation.ValidatePassword
import com.example.mediationapp.presentor.ui_events.LoadingFinish
import com.example.mediationapp.presentor.ui_events.LoadingProgress
import com.example.mediationapp.presentor.ui_events.LoadingState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val validateEmail = ValidateEmail()
    private val validatePassword = ValidatePassword()
    private val authorizationRepository = AuthorizationRepository()


    var state = MutableLiveData<LoginState>()
    val authorizationResult = MutableLiveData<IdentificationEvent>()
    val progressEvent = MutableLiveData<LoadingState>()

    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    fun submitData(email: String, password: String) {
        progressEvent.value = LoadingProgress

        val emailResult = validateEmail.execute(email)
        val passwordResult = validatePassword.execute(password)

        state.value = LoginState(
            emailError = emailResult.errorMessage,
            passwordError = passwordResult.errorMessage,
        )
        val hasError = listOf(
            emailResult,
            passwordResult,
        ).any { !it.successful }
        if (hasError) return

        viewModelScope.launch {
            validationEventChannel.send(ValidationEvent.Success)
        }
    }

    fun getResult() {
        viewModelScope.launch {
            authorizationRepository.authorizationResult.collect {
                authorizationResult.value = it
                progressEvent.value = LoadingFinish
            }
        }
    }

    fun signInUser(email: String, password: String) {
        if (validateLoginInputString(email, password)) {
            viewModelScope.launch {
                authorizationRepository.signInUser(email, password)
            }
        }
    }
    sealed class ValidationEvent {
        object Success : ValidationEvent()
    }

    private fun validateLoginInputString(email: String, password: String): Boolean {
        return !(email.isEmpty() || password.isEmpty())
    }

}