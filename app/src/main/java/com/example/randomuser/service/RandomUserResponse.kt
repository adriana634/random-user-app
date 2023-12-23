package com.example.randomuser.service

data class RandomUserResponse(
    val results: List<RandomUser>,
    val info: UserInfo
)