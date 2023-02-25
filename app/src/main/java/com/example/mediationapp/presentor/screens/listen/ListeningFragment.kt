package com.example.mediationapp.presentor.screens.listen

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.view.size
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mediationapp.R
import com.example.mediationapp.databinding.FragmentListeningBinding
import com.example.mediationapp.domain.model.MeditationElement
import com.example.mediationapp.domain.model.MeditationMusic
import com.example.mediationapp.presentor.adapters.listen_music_adapter.MusicPagerAdapter
import com.example.mediationapp.presentor.adapters.time_adapter.TimeAdapter
import com.example.mediationapp.presentor.screens.meditation_music.MeditationMusicFragment
import com.example.mediationapp.presentor.screens.meditation_music.MeditationMusicViewModel
import com.example.mediationapp.presentor.ui_events.divideList
import com.example.mediationapp.presentor.ui_events.fragmentToast
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.launch


class ListeningFragment : Fragment() {

    lateinit var binding: FragmentListeningBinding
    private lateinit var viewModel : ListeningViewModel
    private lateinit var meditationMusicViewModel: MeditationMusicViewModel
    private lateinit var dialog: BottomSheetDialog
    private lateinit var timeRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentListeningBinding.inflate(layoutInflater, container, false)
        viewModel = ViewModelProvider(this)[ListeningViewModel::class.java]
        meditationMusicViewModel = ViewModelProvider(requireActivity())[MeditationMusicViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadUserInfoUI()
        setupListener()
        loadMeditationSounds()

    }

    private fun setUpViewPager(musicList : MutableList<MeditationMusic>) {

        val newList = divideList(musicList)
        val adapter = MusicPagerAdapter(newList)
        val pager = binding.musicViewPager2
        val indicator = binding.pagerIndicator
        binding.musicViewPager2.adapter = adapter
        indicator.attachToPager(pager)

        adapter.onMusicClickListener = {
            meditationMusicViewModel.itemMeditationMusic.value = it
            Log.d("ListeningFragment", "title ---> ${it.songName}")
        }
    }

    private fun setupListener() {
        binding.tvTime.setOnClickListener {
            showBottomSheet()
        }
        binding.imTimeIcon.setOnClickListener {
            showBottomSheet()
        }
        binding.btGo.setOnClickListener {
            val selectedMeditationElement = meditationMusicViewModel.itemMeditationMusic.value
            if (selectedMeditationElement != null){
                findNavController().navigate(R.id.action_listeningFragment_to_meditationMusicFragment)
            }else{
                fragmentToast("Chose music ")
            }

        }
    }
    private fun loadMeditationSounds(){
        lifecycleScope.launch {
            viewModel.getMeditationMusic()
            viewModel.soundsList.observe(viewLifecycleOwner){
                Log.d("ListeningFragment", it.toString())
                setUpViewPager(it.toMutableList())
            }
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
            layoutInflater.inflate(R.layout.bottom_sheet_dialog_time, null)
        dialog = BottomSheetDialog(
            requireContext(),
            R.style.BottomSheetDialogTheme
        )
        dialog.setContentView(dialogView)
        val timeAdapter = TimeAdapter()
        timeRecyclerView =
            dialogView.findViewById(R.id.rv_time_bottom_sheet)
        timeRecyclerView.adapter = timeAdapter
        dialog.show()

        timeAdapter.onSelectedTimeClickListener = {
            binding.tvTime.text = "$it мин"
            dialog.dismiss()
            meditationMusicViewModel.timer.value = it * 60
        }
    }
}




