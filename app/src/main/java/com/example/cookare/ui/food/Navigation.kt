package com.example.cookare.ui.food


import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.cookare.ui.food.detail.DetailScreen


sealed class NavRoute(val route: String) {
    object FoodScreen : NavRoute("FoodScreen_route")
    object Detail : NavRoute("detail_route")
}

@Composable
fun TodoNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavRoute.FoodScreen.route,
    ) {
        composable(NavRoute.FoodScreen.route) {
            FoodScreen {
                navController.navigate(NavRoute.Detail.route + "/${it?.id ?: -1}") {

                }
            }
        }
        composable(
            NavRoute.Detail.route + "/{id}",
            arguments = listOf(navArgument("id") { type = NavType.LongType }),
        ) {
            DetailScreen(selectedId = it.arguments?.getLong("id") ?: -1) {
                navController.navigateUp()
            }
        }

    }


}