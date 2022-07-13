package com.example.cookare.ui.utils

sealed class ScreenRoute(val route: String){
    object HomeScreen: ScreenRoute("home_screen")
    object CollectionScreen: ScreenRoute("collection_screen")
    object LikeScreen: ScreenRoute("like_screen")
    object NotificationScreen: ScreenRoute("notification_screen")
    object PostTemplates: ScreenRoute("template_screen")
<<<<<<< HEAD
    object PostDetails: ScreenRoute("post_details")
=======
>>>>>>> de27e5411309de705b394f1008e6563376c3621a
    object ProfileScreen: ScreenRoute("profile_screen")
}
