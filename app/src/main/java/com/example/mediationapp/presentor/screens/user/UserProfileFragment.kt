package com.example.mediationapp.presentor.screens.user

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.CrossProfileApps
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.example.mediationapp.R


import com.example.mediationapp.presentor.adapters.users_mood_adapter.TypeMediationAdapter
import com.example.mediationapp.databinding.FragmentUserProfileBinding
import com.example.mediationapp.presentor.ui_events.fragmentToast
import com.example.mediationapp.presentor.ui_events.getUCropResultContract
import com.google.android.gms.common.internal.ImagesContract
import com.yalantis.ucrop.UCrop
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.io.File


class UserProfileFragment : Fragment() {

    lateinit var binding: FragmentUserProfileBinding
    private lateinit var adapter: TypeMediationAdapter
    private lateinit var viewModel: UserProfileViewModel

    private val loadCropImage =
        registerForActivityResult(getUCropResultContract()) { uri: Uri? ->
            if (uri != null) {
                viewModel.loadUserImage(uri)
            }
        }
    private val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        if (uri != null) {
            val outputUri = File(requireActivity().filesDir, "croppedImage.jpg").toUri()
            val listUri = listOf(uri, outputUri)
            loadCropImage.launch(listUri)
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentUserProfileBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity())[UserProfileViewModel::class.java]
        setupTypeRecyclerView()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadUserInfoUI()
        setupListener()
    }

    @SuppressLint("SuspiciousIndentation")
    private fun setupTypeRecyclerView() {

        adapter = TypeMediationAdapter()
        binding.rvUsersMood.adapter = adapter
        val layoutManager = GridLayoutManager(context, 2)
        binding.rvUsersMood.layoutManager = layoutManager
        viewModel.getList()
        binding.progressBar.isVisible = true
        viewModel.list.observe(viewLifecycleOwner) {
            binding.progressBar.isVisible = false
            adapter.submitList(it)
            if (it.isNullOrEmpty()) {
                //if list with elements null, we create one elements for add button
                viewModel.addItem()
            } else {
                adapter.onMediationLongClickListener = {
                    viewModel.deleteItem(it)
                }
                adapter.onMeditationClickListener = {
                    fragmentToast("Clicked")
                }
                adapter.onAddClickListener = {
                    viewModel.addItem()
                }
            }
        }
    }
    private fun loadUserInfoUI() {
        lifecycleScope.launch(Dispatchers.IO) {
            viewModel.getUserInfo()
        }
        viewModel.userLiveData.observe(viewLifecycleOwner) {
            binding.tvUserName.apply {
                text = it?.name
                isVisible = true
            }
            Glide
                .with(requireContext())
                .load(it?.imageUrl)
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(binding.imUserImage)
        }
    }

    private fun setupListener() {
        binding.imUserImage.setOnClickListener {
            getContent.launch(MIMETYP_IMAGE)
        }
    }

    companion object {
        private const val MIMETYP_IMAGE = "image/*"
    }
}

