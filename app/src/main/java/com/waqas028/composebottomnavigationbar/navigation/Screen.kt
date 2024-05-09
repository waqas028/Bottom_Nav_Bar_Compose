package com.waqas028.composebottomnavigationbar.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.waqas028.composebottomnavigationbar.model.BottomNavigationItem

sealed class Screen(val route: String) {

    data object HomeScreen : Screen("HomeScreen")
    data object InboxScreen : Screen("InboxScreen")
    data object NotificationScreen : Screen("NotificationScreen")
    data object ProfileScreen : Screen("ProfileScreen")
}

class SokarNavigationActions(private val navController: NavHostController) {

    fun navigateTo(destination: BottomNavigationItem) {
        navController.navigate(destination.route) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
}

val BOTTOM_MENU_LIST = listOf(
    BottomNavigationItem(
        title = "Home",
        route = Screen.HomeScreen.route,
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home,
    ),
    BottomNavigationItem(
        title = "Inbox",
        route = Screen.InboxScreen.route,
        selectedIcon = Icons.Filled.Email,
        unselectedIcon = Icons.Outlined.Email,
    ),
    BottomNavigationItem(
        title = "Notification",
        route = Screen.NotificationScreen.route,
        selectedIcon = Icons.Filled.Notifications,
        unselectedIcon = Icons.Outlined.Notifications,
    ),
    BottomNavigationItem(
        title = "Profile",
        route = Screen.ProfileScreen.route,
        selectedIcon = Icons.Filled.Person,
        unselectedIcon = Icons.Outlined.Person,
    )
)