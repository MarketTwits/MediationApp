package com.example.mediationapp.domain.model

import androidx.annotation.Keep


data class User(val uId : String? = null,
           val email : String? = null,
           val name : String? = null,
           val age : String? = null,
           val mediationList : List<MeditationElement> )
{
}