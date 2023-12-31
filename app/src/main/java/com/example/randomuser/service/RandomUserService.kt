package com.example.randomuser.service

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RandomUserService {
    @GET("api/")
    suspend fun getRandomUsers(@Query("results") numberOfUsers: Int): Response<RandomUserResponse>
}
