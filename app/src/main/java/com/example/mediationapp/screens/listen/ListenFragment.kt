package com.example.mediationapp.screens.listen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mediationapp.R
import com.example.mediationapp.databinding.FragmentListenBinding


// TODO: Rename parameter arguments, choose names that match

class ListenFragment : Fragment() {

    lateinit var binding : FragmentListenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View{
        // Inflate the layout for this fragment
        binding = FragmentListenBinding.inflate(inflater,container, false)
        return binding.root
    }
}