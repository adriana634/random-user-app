package com.example.randomuser.features.userList.viewModel

import com.example.randomuser.model.User

class UserListItemViewModel(
    private val user: User,
    val page: Int
) {

    val name: String
        get() = user.name

    val email: String
        get() = user.email

    val pictureThumbnail: String
        get() = user.pictureThumbnail
}