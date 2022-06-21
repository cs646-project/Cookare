package com.example.cookare.ui.component.list

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cookare.ui.component.list.collection.CollectionAndLikeScreen
import com.example.cookare.ui.component.list.collection.TabPage
import com.example.cookare.ui.utils.ScreenRoute

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalAnimationApi::class,
    ExperimentalMaterialApi::class)
@Composable
fun ListScreen() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = ScreenRoute.ListScreen.route){
        composable(route = ScreenRoute.ListScreen.route){
            ListScreenContent(navController = navController)
        }
        composable(route = ScreenRoute.CollectionScreen.route){
            CollectionAndLikeScreen()
        }
        composable(route = ScreenRoute.LikeScreen.route){
            CollectionAndLikeScreen()
        }
    }
}

@Composable
fun ListScreenContent(navController: NavController){
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 50.dp)
    ) {
        Button(
            onClick = {
                navController.navigate(ScreenRoute.CollectionScreen.route)
            },
        ) {
            Text(text = "Collection")
        }
        Spacer(modifier = Modifier.padding(16.dp))
        Button(
            onClick = {
                navController.navigate(ScreenRoute.LikeScreen.route)
            },
        ) {
            Text(text = "Like")
        }
    }
}