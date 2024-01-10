package com.example.randomuser.navigation

interface Navigator {
    fun goBack()
    fun navigateToUserDetails(email: String)
}
