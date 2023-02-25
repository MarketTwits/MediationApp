package com.example.mediationapp.presentor.screens.meditation_music



sealed class PlayerState {
    object Idle : PlayerState()
    object Loading : PlayerState()
    object Playing : PlayerState()
    object Paused : PlayerState()
    object Completed : PlayerState()
    data class Error(val message: String?) : PlayerState()
}
sealed class TimerState {
    object Idle : TimerState()
    object Running : TimerState()
    object Paused : TimerState()
    object Finished : TimerState()
}









