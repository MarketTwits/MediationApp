package com.example.mediationapp.view_models

class Event<T>(
    private val value : T) {
    private var handler : Boolean = true

    fun getValue() : T?{
        if(handler) return null
        handler = true
        return value
    }
}