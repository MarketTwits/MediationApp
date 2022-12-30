package com.example.mediationapp.model

import androidx.annotation.Keep


data class User(val id : String? = null,
           val email : String? = null,
           val password : String? = null,
           val name : String? = null,
           val age : String? = null)
{
}