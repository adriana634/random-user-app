package com.example.randomuser.features.userList.model

/**
 * Data class representing a user in the application.
 *
 * @property name The name of the user.
 * @property email The email address of the user.
 * @property picture The URL of the user's profile picture.
 */
data class User(val name: String, val email: String, val picture: String)