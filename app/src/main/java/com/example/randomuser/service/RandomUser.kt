package com.example.randomuser.service

data class RandomUser(
    val name: UserName,
    val email: String,
    val gender: String,
    val picture: UserPicture,
    val registered: UserRegistered,
    val cell: String
)