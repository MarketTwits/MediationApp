package com.example.mediationapp.presentor.screens.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager


import com.example.mediationapp.presentor.adapters.users_mood_adapter.TypeMediationAdapter
import com.example.mediationapp.databinding.FragmentUserProfileBinding
import kotlinx.coroutines.launch


class UserProfileFragment : Fragment() {

    lateinit var binding: FragmentUserProfileBinding
    private lateinit var adapter: TypeMediationAdapter
    private lateinit var viewModel: UserProfileViewModel


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View{
        // Inflate the layout for this fragment
        binding = FragmentUserProfileBinding.inflate(inflater,container, false)
        viewModel = ViewModelProvider(requireActivity())[UserProfileViewModel::class.java]
        setupTypeRecyclerView()

        return binding.root
    }

    private fun setupTypeRecyclerView(){

    adapter = TypeMediationAdapter()
    binding.rvUsersMood.adapter = adapter
    val layoutManager = GridLayoutManager(context, 2)
    binding.rvUsersMood.layoutManager = layoutManager
            viewModel.getList()
            binding.progressBar.isVisible = true
            viewModel.list.observe(viewLifecycleOwner) {
                binding.progressBar.isVisible = false
                adapter.list = it
                if (it.isNullOrEmpty()) {
                    //if list with elements null, we create one elements for add button
                    viewModel.addItem()
                    adapter.notifyDataSetChanged()
                } else {
                    adapter.onMediationLongClickListener = {
                        viewModel.deleteItem(it)
                        adapter.notifyDataSetChanged()
                    }
                    adapter.onMeditationClickListener = {
                        Toast.makeText(requireContext(), "Clicked", Toast.LENGTH_SHORT).show()
                    }
                    adapter.onAddClickListener = {
                        viewModel.addItem()
                        adapter.notifyDataSetChanged()
                    }
                }
            }
        }



    }

