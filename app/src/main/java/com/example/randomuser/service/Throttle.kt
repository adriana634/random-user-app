package com.example.randomuser.service

import kotlinx.coroutines.delay

class Throttle {
    private var lastRequestTime: Long = 0
    private val delayMillis: Long = 1000

    suspend fun throttle() {
        val currentTime = System.currentTimeMillis()
        val elapsedMillis = currentTime - lastRequestTime

        if (elapsedMillis < delayMillis) {
            delay(delayMillis - elapsedMillis)
        }

        lastRequestTime = System.currentTimeMillis()
    }
}
