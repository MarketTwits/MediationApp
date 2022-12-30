package com.example.mediationapp.screens.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mediationapp.MainActivity
import com.example.mediationapp.databinding.FragmentLoginBinding
import com.example.mediationapp.screens.welcome.EntryActivity
import com.example.mediationapp.servcie.FirebaseService
import com.example.mediationapp.view_models.AuthViewModel
import com.google.firebase.database.*



class LoginFragment : Fragment() {



    lateinit var binding: FragmentLoginBinding
    lateinit var viewModel: AuthViewModel
    private val userDbReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://mediationapp-fbe2c-default-rtdb.asia-southeast1.firebasedatabase.app/")

//    val ref= FirebaseDatabase.getInstance().getReference().child("note");
//    private val uid = FirebaseAuth.getInstance().getCurrentUser()?.getUid();
//
//    init {
//        ref.child(uid.toString()).child("запись")
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View{
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment

        //Open logined user
        binding.btProfile.setOnClickListener {
            checkUser()
        }
            //

//        FirebaseDatabase.getInstance().reference
//            .child(FirebaseRepository.USER_KEY).child("User").setValue("DiadiaMisha")

        //

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[AuthViewModel::class.java]

        setupListeners()
    }
    private fun setupListeners(){
        binding.btSignIn.setOnClickListener {
            signInUser()
        }
    }
    private fun signInUser() {
        viewModel.signInUser(
            binding.edEmail.text.toString(),
            binding.edPassword.text.toString(),
            requireContext()
        )
    }
    private fun checkUser(){
        val firebaseUser = FirebaseService.auth.currentUser
        if(firebaseUser == null){
            //user not logged
            Toast.makeText(requireContext(), "You need sign in in the app ", Toast.LENGTH_SHORT).show()
        }else{
            startActivity(Intent(requireActivity(), MainActivity::class.java))
        }
    }
}