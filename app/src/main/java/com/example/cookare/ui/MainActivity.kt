package com.example.cookare.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.cookare.ui.home.UserState
import com.example.cookare.ui.home.UserStateViewModel
import com.example.cookare.ui.theme.AppThemeState
import com.example.cookare.ui.theme.ComposeCookBookMaterial3Theme
import com.example.cookare.ui.theme.SystemUiController
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val userState by viewModels<UserStateViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            // val appState = rememberCookareAppState()
            CompositionLocalProvider(UserState provides userState) {
                val systemUiController = remember { SystemUiController(window) }
                val appTheme = remember { mutableStateOf(AppThemeState()) }
                BaseView(appTheme.value, systemUiController) {
                    CookareApp()
                }
            }
        }
    }
}

@Composable
fun BaseView(
    appThemeState: AppThemeState,
    systemUiController: SystemUiController?,
    content: @Composable () -> Unit
) {
    ComposeCookBookMaterial3Theme(
        darkTheme = appThemeState.darkTheme,
        colorPallet = appThemeState.pallet
    ) {
        systemUiController?.setStatusBarColor(color = MaterialTheme.colorScheme.onPrimaryContainer, darkIcons = appThemeState.darkTheme)
        content()
    }
}