package com.example.randomuser.service

data class UserInfo(
    val seed: String,
    val results: Int,
    val page: Int,
    val version: String
)