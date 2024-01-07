package com.example.randomuser.navigation

import android.util.Log
import androidx.navigation.NavController
import com.example.randomuser.navigation.AppScreen.UserDetailsScreen.replaceRouteParameter
import javax.inject.Inject

class NavControllerNavigator @Inject constructor() : Navigator {

    companion object {
        // TAG for logging purposes.
        private val TAG = NavControllerNavigator::class.java.simpleName
    }

    private var navController: NavController? = null

    fun setNavController(navController: NavController) {
        this.navController = navController
    }

    override fun navigateToUserDetails(email: String) {
        Log.d(TAG, "Navigating to UserDetailsScreen")
        val route = AppScreen.UserDetailsScreen.replaceRouteParameter(AppRouteParameter.EmailParameter.template, email)
        navController?.navigate(route)
    }
}
