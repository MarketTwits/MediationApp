package com.example.mediationapp.presentor.ui_events

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContract
import androidx.fragment.app.Fragment
import com.example.mediationapp.R
import com.example.mediationapp.presentor.screens.main.MainActivity
import com.yalantis.ucrop.UCrop

fun Fragment.fragmentToast(text : String? = null, stringResource : Int? = null){
    if(text != null){
        Toast.makeText(requireContext(), text, Toast.LENGTH_SHORT).show()
    }
    if(stringResource != null){
        Toast.makeText(requireContext(), stringResource, Toast.LENGTH_SHORT).show()
    }
}
fun Fragment.getUCropResultContract() :  ActivityResultContract<List<Uri>, Uri>{
    val uCropActivityResultContracts = object : ActivityResultContract<List<Uri>, Uri>() {
        override fun createIntent(context: Context, input: List<Uri>): Intent {
            val inptuUri = input[0]
            val output = input[1]
            val uCrop = UCrop.of(inptuUri, output)
            return uCrop.getIntent(context)
        }
        override fun parseResult(resultCode: Int, intent: Intent?): Uri {
            return UCrop.getOutput(intent!!)!!
        }
    }
    return uCropActivityResultContracts
}