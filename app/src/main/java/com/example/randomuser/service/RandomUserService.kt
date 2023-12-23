package com.example.randomuser.service

import retrofit2.Response
import retrofit2.http.GET

interface RandomUserService {
    @GET("api/")
    suspend fun getRandomUsers(): Response<RandomUserResponse>
}
