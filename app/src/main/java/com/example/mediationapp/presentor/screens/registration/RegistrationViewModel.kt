package com.example.mediationapp.presentor.screens.registration

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mediationapp.data.firebase.FirebaseRepository
import com.example.mediationapp.domain.use_case.*
import com.example.mediationapp.domain.validation.RegisterError
import com.example.mediationapp.domain.validation.RegisterSuccess
import com.example.mediationapp.domain.validation.RegistrationEvent
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.io.IOException

class RegistrationViewModel : ViewModel() {


    private val registrationRepository = FirebaseRepository()

    val dataResult = registrationRepository.registrationResult

    private val validateEmail: ValidateEmail = ValidateEmail()
    private val validatePassword: ValidatePassword = ValidatePassword()
    private val validateName: ValidateName = ValidateName()
    private val validateAge: ValidateAge = ValidateAge()

    var state = MutableLiveData<RegistrationState>()


    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    fun submitData(email: String, password: String, name: String, age: String) {
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
        if (validateRegistrationInputString(email, password, name, age)) {
            viewModelScope.launch {
                FirebaseRepository().createUser(email, password, name, age)
            }
        }
    }

    private fun validateRegistrationInputString(
        email: String,
        password: String,
        name: String,
        age: String,
    ): Boolean {
        return !(email.isEmpty() || password.isEmpty() || name.isEmpty() || age.isEmpty())
    }

    fun getResult() {
        viewModelScope.launch {

//            registrationRepository.registrationResult.asSharedFlow().collect{
//                registrationResult.value = it
//            }
        }
    }
}