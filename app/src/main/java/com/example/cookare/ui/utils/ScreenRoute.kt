package com.example.cookare.ui.utils

sealed class ScreenRoute(val route: String){
    object ListScreen: ScreenRoute("list_screen")
    object CollectionScreen: ScreenRoute("collection_screen")
    object LikeScreen: ScreenRoute("like_screen")
}
