package com.example.mediationapp.presentor.ui_events

import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.mediationapp.R
import com.example.mediationapp.presentor.screens.main.MainActivity

fun Fragment.fragmentToast(text : String? = null, stringResource : Int? = null){
    if(text != null){
        Toast.makeText(requireContext(), text, Toast.LENGTH_SHORT).show()
    }
    if(stringResource != null){
        Toast.makeText(requireContext(), stringResource, Toast.LENGTH_SHORT).show()
    }
}