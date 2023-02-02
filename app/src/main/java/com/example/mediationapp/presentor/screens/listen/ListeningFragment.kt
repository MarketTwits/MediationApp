package com.example.mediationapp.presentor.screens.listen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mediationapp.R
import com.example.mediationapp.databinding.FragmentListeningBinding
import com.example.mediationapp.presentor.adapters.listen_music_adapter.MusicPagerAdapter
import com.example.mediationapp.presentor.adapters.time_adapter.TimeAdapter
import com.example.mediationapp.presentor.ui_events.divideList
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.launch


class ListeningFragment : Fragment() {

    lateinit var binding: FragmentListeningBinding
    private lateinit var viewModel : ListeningViewModel
    private lateinit var dialog: BottomSheetDialog
    private lateinit var timeRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentListeningBinding.inflate(layoutInflater, container, false)
        viewModel = ViewModelProvider(this)[ListeningViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadUserInfoUI()
        setUpViewPager()
        setupListener()

    }

    private fun setUpViewPager() {
        val list = listOf(
            "Game", "Car", "Map", "var", "Map", "Map", "Map", "Map", "MAP", "Game", "Car", "Map",
            "Car", "Map", "var", "Map", "Map", "Map", "Map", "Map"
        )
        val newList = divideList(list)
        val adapter = MusicPagerAdapter(newList)
        val pager = binding.musicViewPager2
        val indicator = binding.pagerIndicator
        binding.musicViewPager2.adapter = adapter
        indicator.attachToPager(pager)
    }

    private fun setupListener() {
        binding.tvTime.setOnClickListener {
            showBottomSheet()
        }
        binding.imTimeIcon.setOnClickListener {
            showBottomSheet()
        }
    }
    private fun loadUserInfoUI() {
        lifecycleScope.launch {
            viewModel.getUserInfo()
        }
        viewModel.userLiveData.observe(viewLifecycleOwner) {
            Glide
                .with(requireContext())
                .load(it?.imageUrl)
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(binding.imProfileImageMain)
        }
    }

    private fun showBottomSheet() {
        //tv_time and timeIcon make click
        val dialogView =
            layoutInflater.inflate(com.example.mediationapp.R.layout.bottom_sheet_dialog_time, null)
        dialog = BottomSheetDialog(
            requireContext(),
            com.example.mediationapp.R.style.BottomSheetDialogTheme
        )
        dialog.setContentView(dialogView)

        val timeAdapter = TimeAdapter()
        timeRecyclerView =
            dialogView.findViewById(com.example.mediationapp.R.id.rv_time_bottom_sheet)
        timeRecyclerView.adapter = timeAdapter
        dialog.show()

        timeAdapter.onSelectedTimeClickListener = {
            binding.tvTime.text = it
            dialog.dismiss()
        }
    }
}




