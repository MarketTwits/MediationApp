package com.example.mediationapp.presentor.screens.meditation_music.services

import android.content.Context
import android.net.Uri
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import java.lang.ref.WeakReference

object ExoPlayerSingleton {
    private var exoPlayerRef: WeakReference<ExoPlayer>? = null
    private var lastPlayedUri: Uri? = null

    fun getExoPlayer(context: Context): ExoPlayer {
        if (exoPlayerRef?.get() == null) {
            val exoPlayer = ExoPlayer.Builder(context).build()
            exoPlayerRef = WeakReference(exoPlayer)
            return exoPlayer
        }
        return exoPlayerRef!!.get()!!
    }
    fun playMedia(context: Context, uri: Uri) {
        val exoPlayer = getExoPlayer(context)

        // If the player is already playing the same media, do nothing
        if (lastPlayedUri == uri && exoPlayer.playWhenReady) {
            return
        }
        // Release the previous media item and reset the player
        exoPlayer.setMediaItem(MediaItem.fromUri(uri))
        exoPlayer.prepare()
        exoPlayer.playWhenReady = true

        lastPlayedUri = uri
    }

    fun pauseMedia() {
        val exoPlayer = exoPlayerRef?.get()

        exoPlayer?.playWhenReady = false
    }

    fun resumeMedia() {
        val exoPlayer = exoPlayerRef?.get()

        exoPlayer?.playWhenReady = true
    }

    fun stopMedia() {
        val exoPlayer = exoPlayerRef?.get()

        exoPlayer?.stop()
        exoPlayer?.clearMediaItems()
        lastPlayedUri = null
    }

    fun release() {
        val exoPlayer = exoPlayerRef?.get()

        exoPlayer?.release()
        exoPlayerRef = null
        lastPlayedUri = null
    }
}