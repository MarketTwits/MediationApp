package com.example.mediationapp.presentor.ui_events


sealed class LoadingState()
object LoadingFinish : LoadingState()
object LoadingProgress : LoadingState()