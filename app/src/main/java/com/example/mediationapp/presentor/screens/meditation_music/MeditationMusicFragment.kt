package com.example.mediationapp.presentor.screens.meditation_music

import android.R
import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController

import com.example.mediationapp.databinding.FragmentMeditationMusicBinding
import com.example.mediationapp.databinding.SoundControlPanelLayoutBinding
import com.example.mediationapp.domain.model.MeditationMusic
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import java.lang.ref.WeakReference


class MeditationMusicFragment : Fragment() {

    private lateinit var binding: FragmentMeditationMusicBinding
    private lateinit var viewModel: MeditationMusicViewModel
    private lateinit var soundPanelBinding: SoundControlPanelLayoutBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentMeditationMusicBinding.inflate(layoutInflater, container, false)
        soundPanelBinding = SoundControlPanelLayoutBinding.bind(binding.soundPanel.root)
        viewModel = ViewModelProvider(requireActivity())[MeditationMusicViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
        setupUi()

        viewModel.initPlayer(requireContext())
    }

    private fun setupUi() {

        viewModel.itemMeditationMusic.observe(viewLifecycleOwner) { music ->
            binding.tvMusicName.text = music.songName
            launchMusic()
        }
       observeTimerMusic()
    }


    private fun setupListeners() {
        binding.btPlay.setOnClickListener {
            viewModel.togglePlayerState()
        }
        soundPanelBinding.imChangeSound.setOnClickListener {

        }
        viewModel.sharedMillisUntilFinished.observe(viewLifecycleOwner){
            binding.tvCurrentTime.text = it.toString()
        }

    }

    private fun launchMusic() {
        viewModel.initTimer()
        viewModel.timerState.observe(viewLifecycleOwner) {
            Log.d("ListeningFragment", it.toString())
        }
    }

    private fun observeTimerMusic(){
        viewModel.timerState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is TimerState.Running -> {
                    viewModel.startPlayer()
                }
                is TimerState.Paused -> {
                    viewModel.stopPlayer()
                }
                is TimerState.Finished -> {
                    viewModel.finishedPlayer()
                }
                else -> {

                }
            }
        }
    }

    companion object {
        const val TIMER_DURATION = 60000L
        const val TIMER_INTERVAL = 1000L
    }
}











