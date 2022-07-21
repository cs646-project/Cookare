package com.example.cookare.ui.component.food

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.material.*
import com.example.cookare.PickPicture
import androidx.compose.material.OutlinedTextField
import androidx.compose.ui.Modifier
import com.example.cookare.ui.component.home.CommunityContent
import com.example.cookare.ui.component.home.NamesBar
import com.example.cookare.ui.component.home.SearchBar
import com.example.cookare.ui.component.home.TopBar
import com.example.cookare.ui.theme.BackgroundWhite

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
            SearchBar()




    }
}