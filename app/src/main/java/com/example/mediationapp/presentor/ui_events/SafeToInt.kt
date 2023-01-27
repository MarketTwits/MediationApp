package com.example.mediationapp.presentor.ui_events

class SafeToInt() {
     fun safeToInt(string: String): Int {
        if (string.isNotBlank()) {
            return string.toInt()
        } else {
            return 0
        }
    }
}