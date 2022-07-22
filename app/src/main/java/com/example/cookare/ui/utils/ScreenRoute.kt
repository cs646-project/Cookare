package com.example.cookare.ui.utils

sealed class ScreenRoute(val route: String){
    object HomeScreen: ScreenRoute("home_screen")
    object CollectionScreen: ScreenRoute("collection_screen")
    object LikeScreen: ScreenRoute("like_screen")
    object NotificationScreen: ScreenRoute("notification_screen")
    object PostTemplates: ScreenRoute("template_screen")
    object PostDetails: ScreenRoute("post_details")
    object ProfileScreen: ScreenRoute("profile_screen")
}
