package com.example.cookare.ui

import FaIcons
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cookare.BottomNavType
import com.example.cookare.R
import com.example.cookare.ui.components.CookareScaffold
import com.example.cookare.ui.food.FoodScreen
import com.example.cookare.ui.home.*
import com.example.cookare.ui.home.collection.CollectionAndLikeScreen
import com.example.cookare.ui.home.collection.TabPage
import com.example.cookare.ui.home.notification.NotificationScreen
import com.example.cookare.ui.list.ListScreen
import com.example.cookare.ui.theme.CookareTheme
import com.example.cookare.ui.utils.ScreenRoute
import com.example.cookare.ui.utils.TestTags
import com.guru.fontawesomecomposelib.FaIcon

@Composable
fun CookareApp() {
    CookareTheme {
        val appState = rememberCookareAppState()
        val navController = rememberNavController()
        val userStateVM = UserState.current
        CookareScaffold(
            bottomBar = {
                if (appState.shouldShowBottomBar && userStateVM.isLoggedIn) {
                    CookareBottomBar(
                        tabs = appState.bottomBarTabs,
                        currentRoute = appState.currentRoute!!,
                        navigateToRoute = appState::navigateToBottomBarRoute
                    )
                }
            },
            scaffoldState = appState.scaffoldState
        ) { innerPaddingModifier ->
            NavHost(
                navController = appState.navController,
                startDestination = MainDestinations.HOME_ROUTE,
                modifier = Modifier.padding(innerPaddingModifier)
            ) {
                cookareNavGraph(
                    onSnackSelected = appState::navigateToSnackDetail,
                    upPress = appState::upPress,
                    navController = navController,
                    userStateVM = userStateVM
                )
            }
        }
    }
}

@Composable
fun HomeScreenNavigate() {
    val navController = rememberNavController()
    val upPress = { upPress(navController) }

    NavHost(navController = navController, startDestination = ScreenRoute.HomeScreen.route){
        composable(route = ScreenRoute.HomeScreen.route){
            HomeScreen(navController = navController)
        }
        composable(route = ScreenRoute.CollectionScreen.route){
            CollectionAndLikeScreen(TabPage.Collection, navController = navController)
        }
        composable(route = ScreenRoute.LikeScreen.route){
            CollectionAndLikeScreen(TabPage.Like, navController = navController)
        }
        composable(route = ScreenRoute.NotificationScreen.route){
            NotificationScreen(upPress)
        }
        composable(route = ScreenRoute.PostTemplates.route){
            PostTemplate(navController = navController)
        }
        cookareNavGraph(
            upPress
        )
    }
}

fun upPress(navController: NavHostController) {
    navController.navigateUp()
}

private fun NavGraphBuilder.cookareNavGraph(
    upPress: () -> Unit
) {
    composable(
        "notification_screen"
    ) {
        NotificationScreen(upPress)
    }
}

private fun NavGraphBuilder.cookareNavGraph(
    onSnackSelected: (Long, NavBackStackEntry) -> Unit,
    upPress: () -> Unit,
    navController: NavController,
    userStateVM: UserStateViewModel
) {
    navigation(
        route = MainDestinations.HOME_ROUTE,
        startDestination = HomeSections.FEED.route
    ) {
        addHomeGraph(
            onSnackSelected,
            navController,
            userStateVM)
    }
    composable(
        "${MainDestinations.SNACK_DETAIL_ROUTE}/{${MainDestinations.SNACK_ID_KEY}}",
        arguments = listOf(navArgument(MainDestinations.SNACK_ID_KEY) { type = NavType.LongType })
    ) { backStackEntry ->
        val arguments = requireNotNull(backStackEntry.arguments)
        val snackId = arguments.getLong(MainDestinations.SNACK_ID_KEY)
        // SnackDetail(snackId, upPress)
    }
}
