package com.example.mediationapp.screens.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager

import com.example.mediationapp.adapters.users_mood.TypeMediationAdapter
import com.example.mediationapp.databinding.FragmentUserProfileBinding


class UserProfileFragment : Fragment() {
    lateinit var binding: FragmentUserProfileBinding
    private lateinit var adapter: TypeMediationAdapter
    private lateinit var viewModel: UserProfileViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentUserProfileBinding.inflate(inflater,container, false)
        viewModel = ViewModelProvider(requireActivity())[UserProfileViewModel::class.java]
        setupTypeRecyclerView()


        return binding.root
    }

    private fun setupTypeRecyclerView(){

    adapter = TypeMediationAdapter(viewModel.list.value!!)
    binding.rvUsersMood.adapter = adapter
    val layoutManager = GridLayoutManager(context, 2)
    binding.rvUsersMood.layoutManager = layoutManager

    viewModel.list.observe(viewLifecycleOwner) {
        if(it.isEmpty()){
            viewModel.addItem(viewModel.createItem())
        }
        adapter.setMediationList(it)
    }

    }
}