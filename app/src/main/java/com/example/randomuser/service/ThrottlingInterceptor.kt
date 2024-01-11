package com.example.randomuser.service

import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class ThrottlingInterceptor : Interceptor {
    private val throttler = Throttle()

    override fun intercept(chain: Interceptor.Chain): Response {
        // Enforce a delay before the next request
        runBlocking {
            throttler.throttle()
        }

        // Continue with the original request
        return chain.proceed(chain.request())
    }
}
