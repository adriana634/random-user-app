package com.example.randomuser.model

import java.util.Date

/**
 * Data class representing a user in the application.
 *
 * @property name The name of the user.
 * @property email The email address of the user.
 * @property pictureThumbnail The URL of the user's profile picture (thumbnail).
 * @property pictureLarge The URL of the user's profile picture (large).
 */
data class User(
    val name: String,
    val email: String,
    val gender: String,
    val pictureThumbnail: String,
    val pictureLarge: String,
    val registeredDate: Date,
    val cell: String
)