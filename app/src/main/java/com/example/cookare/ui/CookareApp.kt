package com.example.cookare.ui

<<<<<<< HEAD
import android.content.Intent
=======

>>>>>>> de27e5411309de705b394f1008e6563376c3621a
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat.startActivity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cookare.ui.components.CookareScaffold
import com.example.cookare.ui.food.RecipeDetail
import com.example.cookare.ui.home.*
import com.example.cookare.ui.home.collection.CollectionAndLikeScreen
import com.example.cookare.ui.home.collection.TabPage
import com.example.cookare.ui.home.notification.NotificationScreen
<<<<<<< HEAD
=======
import com.example.cookare.ui.list.ListScreen
>>>>>>> de27e5411309de705b394f1008e6563376c3621a
import com.example.cookare.ui.profile.ProfileScreen
import com.example.cookare.ui.theme.CookareTheme
import com.example.cookare.ui.utils.ScreenRoute
import com.google.android.material.internal.ContextUtils.getActivity

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
            HomeScreen(navController = navController, hiltViewModel())
        }
        composable(route = ScreenRoute.ProfileScreen.route){
            ProfileScreen(navController = navController)
        }
        composable(route = ScreenRoute.ProfileScreen.route){
            ProfileScreen(navController = navController)
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
            PostTemplate(navController = navController, hiltViewModel())
        }
        composable(route = ScreenRoute.PostDetails.route){
            RecipeDetail(navController = navController, hiltViewModel())
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
