package com.example.cookare.ui.utils

sealed class ScreenRoute(val route: String){
    object HomeScreen: ScreenRoute("home_screen")
    object NotificationScreen: ScreenRoute("notification_screen")
    object PostTemplates: ScreenRoute("template_screen")
    object ProfileScreen: ScreenRoute("profile_screen")
    object EditPost: ScreenRoute("edit_post")
    object ListScreen: ScreenRoute("list_screen")
}
