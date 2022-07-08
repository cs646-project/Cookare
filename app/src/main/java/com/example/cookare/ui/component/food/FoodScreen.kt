package com.example.cookare.ui.component.food

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.material.*
import com.example.cookare.PickPicture

//import com.example.cookare.RequestContentPermission

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalAnimationApi::class,
    ExperimentalMaterialApi::class
)
@Composable
fun FoodScreen() {

    Scaffold() {
        PickPicture()
    }
}