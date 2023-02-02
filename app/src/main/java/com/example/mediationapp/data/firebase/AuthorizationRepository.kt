package com.example.mediationapp.data.firebase

import ResponseError
import ResponseEvent
import ResponseSuccess



import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class AuthorizationRepository {
    private val auth = FirebaseAuth.getInstance()
    private val scope = CoroutineScope(Dispatchers.Main)
    val authorizationResult = MutableSharedFlow<ResponseEvent>()


    fun signInUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                    scope.launch {
                        authorizationResult.emit(ResponseSuccess(it.toString()))
                    }
            }
            .addOnFailureListener {
                scope.launch {
                    authorizationResult.emit(ResponseError(it))
                }
            }
    }
}