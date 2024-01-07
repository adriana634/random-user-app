package com.example.randomuser.navigation

sealed class AppScreen(val route: String) {

    data object UserListScreen : AppScreen("userListScreen")
    data object UserDetailsScreen : AppScreen("userDetailsScreen/${AppRouteParameter.EmailParameter.template}")

    fun AppScreen.replaceRouteParameter(parameter: String, value: String): String {
        if (!route.contains(parameter)) {
            throw IllegalArgumentException("Parameter '$parameter' not found in route '$route'")
        }
        return route.replace(parameter, value)
    }
}
