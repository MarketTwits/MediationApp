package com.example.mediationapp.servcie

import com.google.firebase.auth.FirebaseAuth

object FirebaseService {
    val auth = FirebaseAuth.getInstance()

    const val SHOW_WELCOME = 0
    const val SHOW_INVALID_PASSWARD_OR_LOGIN = 1;
    private var mAction: Int? = null

    fun Action(action: Int) {
        mAction = action
    }

}