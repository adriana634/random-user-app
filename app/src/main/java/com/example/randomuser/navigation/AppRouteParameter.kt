package com.example.randomuser.navigation

sealed class AppRouteParameter(val template: String, val name: String) {

    data object EmailParameter : AppRouteParameter("{userEmail}", "userEmail")
}