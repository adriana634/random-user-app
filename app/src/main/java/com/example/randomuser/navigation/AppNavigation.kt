package com.example.randomuser.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.randomuser.features.userDetails.ui.UserDetailsScreen
import com.example.randomuser.features.userList.ui.UserListScreen
import com.example.randomuser.features.userList.viewModel.UserListViewModel

@Composable
fun AppNavigation(navControllerNavigator: NavControllerNavigator, userListViewModel: UserListViewModel) {
    val navController = rememberNavController()
    navControllerNavigator.setNavController(navController)

    NavHost(
        navController = navController,
        startDestination = AppScreen.UserListScreen.route
    ) {
        composable(AppScreen.UserListScreen.route) {
            UserListScreen(navControllerNavigator, userListViewModel)
        }

        composable(
            route = AppScreen.UserDetailsScreen.route,
            arguments = listOf(navArgument(AppRouteParameter.EmailParameter.name) { type = NavType.StringType })
        ) { backStackEntry ->
            val userEmail = backStackEntry.arguments?.getString(AppRouteParameter.EmailParameter.name)
            val user = userEmail?.let { userListViewModel.findUserModelByEmail(it) }
            user?.let {
                UserDetailsScreen(navigator = navControllerNavigator, user = it)
            }
        }
    }
}
