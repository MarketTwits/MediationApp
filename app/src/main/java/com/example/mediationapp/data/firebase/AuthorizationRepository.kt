package com.example.mediationapp.data.firebase

import IdentificationError
import IdentificationEvent
import IdentificationSuccess



import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class AuthorizationRepository {
    private val auth = FirebaseAuth.getInstance()
    private val scope = CoroutineScope(Dispatchers.Main)
    val authorizationResult = MutableSharedFlow<IdentificationEvent>()


    fun signInUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                    scope.launch {
                        authorizationResult.emit(IdentificationSuccess(it.toString()))
                    }
            }
            .addOnFailureListener {
                scope.launch {
                    authorizationResult.emit(IdentificationError(it))
                }
            }
    }
}