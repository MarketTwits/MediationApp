package com.example.mediationapp.presentor.screens.welcome

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.mediationapp.R
import com.example.mediationapp.databinding.FragmentWelcomeBinding
import com.example.mediationapp.presentor.screens.login.LoginFragment
import com.example.mediationapp.presentor.screens.registration.RegistrationFragment


class WelcomeFragment : Fragment() {

    lateinit var viewModel: WelcomeViewModel

    lateinit var binding: FragmentWelcomeBinding
    // TODO: Rename and change types of parameters

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentWelcomeBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[WelcomeViewModel::class.java]
        binding.signInButton.setOnClickListener {
            fragmentNavigation(R.id.entry_fragment_container, LoginFragment())
        }
        binding.tvRegistration.setOnClickListener {
            fragmentNavigation(R.id.entry_fragment_container, RegistrationFragment())
        }
    }

    private fun fragmentNavigation(fragmentCotnainer: Int, fragment: Fragment) {
        parentFragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(fragmentCotnainer, fragment)
            .commit()
    }


}