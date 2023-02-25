package com.example.mediationapp.presentor.screens.meditation_music

import android.content.Context
import android.util.Log
import androidx.core.net.toUri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.androchef.happytimer.countdowntimer.HappyTimer
import com.example.mediationapp.domain.model.MeditationMusic
import com.example.mediationapp.presentor.screens.meditation_music.services.ExoPlayerSingleton
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.Player

class MeditationMusicViewModel : ViewModel() {


    val itemMeditationMusic = MutableLiveData<MeditationMusic>()

     lateinit var player: ExoPlayer

    var timer = MutableLiveData<Int>(15)
      private val _timer : LiveData<Int> = timer

    var happyTimer = HappyTimer(timer.value!!)

    private val _timerState = MutableLiveData<TimerState>(TimerState.Paused)
        val timerState: LiveData<TimerState> = _timerState

    private val _sharedMillisUntilFinished = MutableLiveData<Int>()
    val sharedMillisUntilFinished: LiveData<Int> = _sharedMillisUntilFinished

    fun togglePlayerState() {
        _timerState.value = when (_timerState.value) {
            is TimerState.Running -> TimerState.Paused
            is TimerState.Paused -> TimerState.Running
            is TimerState.Finished -> TimerState.Paused
            else -> throw java.lang.RuntimeException()
        }
        if (_timerState.value == TimerState.Finished){
            initTimer()
        }
    }

    fun initPlayer(context: Context) {
        player = ExoPlayerSingleton.getExoPlayer(context)
        val uri = itemMeditationMusic.value!!.songUrl
        ExoPlayerSingleton.playMedia(context, uri!!.toUri())

        player.addListener(object : Player.Listener{
            override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                if (playbackState == Player.STATE_ENDED) {
                    if (_sharedMillisUntilFinished.value!! > 0){
                        player.seekToDefaultPosition()
                        startPlayer()
                    }else{
                        finishedPlayer()
                    }
                }
            }
        })
    }

    fun stopPlayer() {
        happyTimer.pause()
        player.pause()
    }

    fun startPlayer() {
        happyTimer.resume()
        player.prepare()
        player.play()
        Log.d("ListeningFragment", "player resumed")
    }
    fun finishedPlayer(){
        _timerState.value = TimerState.Paused
        player.seekToDefaultPosition()
        happyTimer = HappyTimer(_timer.value!!)
        initTimer()
    }

    fun initTimer() {
        //set OnTickListener for getting updates on time. [Optional]
        happyTimer.setOnTickListener(object :HappyTimer.OnTickListener{
            //OnTick
            override fun onTick(completedSeconds: Int, remainingSeconds: Int) {
                Log.d("ListeningFragment", remainingSeconds.toString())
                _sharedMillisUntilFinished.value = remainingSeconds
                _timerState.value = TimerState.Running

            }

            //OnTimeUp
            override fun onTimeUp() {
                Log.d("ListeningFragment", "onTimerFinish")
                _timerState.value = TimerState.Finished
                finishedPlayer()
            }
        })
    }
}
