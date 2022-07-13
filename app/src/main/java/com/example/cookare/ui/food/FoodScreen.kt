package com.example.cookare.ui.food

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.material.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.cookare.PickPicture
import com.example.cookare.viewModels.PostRecipeViewModel

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