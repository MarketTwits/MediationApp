package com.example.mediationapp.presentor.screens.registration

import IdentificationEvent
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mediationapp.data.firebase.RegistrationRepository
import com.example.mediationapp.domain.use_case.validation.*
import com.example.mediationapp.presentor.ui_events.LoadingFinish
import com.example.mediationapp.presentor.ui_events.LoadingProgress
import com.example.mediationapp.presentor.ui_events.LoadingState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class RegistrationViewModel : ViewModel() {


    private val registrationRepository = RegistrationRepository()

    private val validateEmail: ValidateEmail = ValidateEmail()
    private val validatePassword: ValidatePassword = ValidatePassword()
    private val validateName: ValidateName = ValidateName()
    private val validateAge: ValidateAge = ValidateAge()

    var state = MutableLiveData<RegistrationState>()
    val registrationResult = MutableLiveData<IdentificationEvent>()
    val progressEvent = MutableLiveData<LoadingState>()


    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    fun submitData(email: String, password: String, name: String, age: String) {
        progressEvent.value = LoadingProgress

        val emailResult = validateEmail.execute(email)
        val passwordResult = validatePassword.execute(password)
        val nameResult = validateName.execute(name)
        val ageResult = validateAge.execute(age)

        state.value = RegistrationState(
            emailError = emailResult.errorMessage,
            passwordError = passwordResult.errorMessage,
            nameError = nameResult.errorMessage,
            ageError = ageResult.errorMessage,
        )
        val hasError = listOf(
            emailResult,
            passwordResult,
            nameResult,
            ageResult
        ).any { !it.successful }
        if (hasError) return

        viewModelScope.launch {
            validationEventChannel.send(ValidationEvent.Success)
        }
    }

    sealed class ValidationEvent {
        object Success : ValidationEvent()
    }

    fun signUpUser(email: String, password: String, name: String, age: String) {
            viewModelScope.launch {
                registrationRepository.createUser(email, password, name, age)
            }
    }

    fun getResult() {
        viewModelScope.launch {
            registrationRepository.registrationResult.collect {
                registrationResult.value = it
                progressEvent.value = LoadingFinish
            }
        }
    }
}