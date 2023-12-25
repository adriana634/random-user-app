package com.example.randomuser.features.userList.viewModel

import com.example.randomuser.model.User

class UserListItemViewModel(
    private val user: User,
    private val onUserItemClickListener: OnUserItemClickListener? = null
) {

    val name: String
        get() = user.name

    val email: String
        get() = user.email

    val pictureThumbnail: String
        get() = user.pictureThumbnail

    fun onUserItemClick() {
        onUserItemClickListener?.onUserItemClick(user.email)
    }
}