package com.example.cookare

import FaIcons
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.Crossfade
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material3.*
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.cookare.ui.theme.AppThemeState
import com.example.cookare.ui.theme.*
import com.example.cookare.ui.utils.TestTags
import com.example.cookare.ui.component.food.FoodScreen
import com.example.cookare.ui.component.list.ListScreen
import com.example.cookare.ui.component.home.HomeScreen
import com.example.cookare.ui.component.setting.SettingScreen
import com.example.cookare.ui.component.home.PalletMenu
import com.guru.fontawesomecomposelib.FaIcon
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalAnimationApi::class,
        ExperimentalFoundationApi::class,
        ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val systemUiController = remember { SystemUiController(window) }
            val appTheme = remember { mutableStateOf(AppThemeState()) }
            BaseView(appTheme.value, systemUiController) {
                MainAppContent(appTheme)
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
    val color = when (appThemeState.pallet) {
        ColorPallet.GREEN -> green700
        ColorPallet.BLUE -> blue700
        ColorPallet.ORANGE -> orange700
        ColorPallet.PURPLE -> purple700
        else -> green700
    }
    ComposeCookBookMaterial3Theme(
        darkTheme = appThemeState.darkTheme,
        colorPallet = appThemeState.pallet
    ) {
        systemUiController?.setStatusBarColor(color = androidx.compose.material3.MaterialTheme.colorScheme.onPrimaryContainer, darkIcons = appThemeState.darkTheme)
        content()
    }
}

@OptIn(ExperimentalAnimationApi::class,
    ExperimentalFoundationApi::class,
    ExperimentalMaterialApi::class)
@Composable
fun HomeScreenContent(
    homeScreen: BottomNavType,
    appThemeState: MutableState<AppThemeState>,
    chooseColorBottomModalState: ModalBottomSheetState, //use for a11y
    modifier: Modifier
) {
    Column(modifier = modifier) {
        Crossfade(homeScreen) { screen ->
            androidx.compose.material3.Surface(color = MaterialTheme.colorScheme.background) {
                when (screen) {
                    BottomNavType.HOME -> HomeScreen()
                    BottomNavType.FOOD -> FoodScreen()
                    BottomNavType.LIST -> ListScreen()
                    BottomNavType.SETTING -> SettingScreen()
                }
            }
        }
    }
}


@OptIn(
    ExperimentalAnimationApi::class,
    ExperimentalFoundationApi::class,
    ExperimentalMaterialApi::class)
@Composable
fun MainAppContent(appThemeState: MutableState<AppThemeState>) {
    //Default home screen state is always HOME
    val homeScreenState = rememberSaveable { mutableStateOf(BottomNavType.HOME) }
    val bottomNavBarContentDescription = stringResource(id = R.string.a11y_bottom_navigation_bar)
    val chooseColorBottomModalState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)

    val coroutineScope = rememberCoroutineScope()

    ModalBottomSheetLayout(
        sheetState = chooseColorBottomModalState,
        sheetContent = {
            PalletMenu(
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) { newPalletSelected ->
                appThemeState.value = appThemeState.value.copy(pallet = newPalletSelected)
                coroutineScope.launch {
                    chooseColorBottomModalState.hide()
                }
            }
        }
    ) {
        val config = LocalConfiguration.current
        val orientation = config.orientation
        if (orientation == Configuration.ORIENTATION_PORTRAIT){
            Column{
                HomeScreenContent(
                    homeScreen = homeScreenState.value,
                    appThemeState = appThemeState,
                    chooseColorBottomModalState = chooseColorBottomModalState,
                    modifier = Modifier.weight(1f)
                )
                BottomNavigationContent(
                    modifier = Modifier
                        .semantics { contentDescription = bottomNavBarContentDescription }
                        .testTag(TestTags.BOTTOM_NAV_TEST_TAG),
                    homeScreenState = homeScreenState
                )
            }
        }else{
            Row(modifier = Modifier.fillMaxSize()) {
                NavigationRailContent(
                    modifier = Modifier
                        .semantics { contentDescription = bottomNavBarContentDescription }
                        .testTag(TestTags.BOTTOM_NAV_TEST_TAG),
                    homeScreenState = homeScreenState
                )
                HomeScreenContent(
                    homeScreen = homeScreenState.value,
                    appThemeState = appThemeState,
                    chooseColorBottomModalState = chooseColorBottomModalState,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
fun BottomNavigationContent(
    modifier: Modifier = Modifier,
    homeScreenState: MutableState<BottomNavType>
) {
    var animate by remember { mutableStateOf(false) }
    NavigationBar(
        modifier = modifier,
    ){
        NavigationBarItem(
            icon = {
                FaIcon(
                    faIcon = FaIcons.Home,
                    tint = androidx.compose.material3.LocalContentColor.current.copy(alpha = LocalContentAlpha.current)
                )
            },
            selected = homeScreenState.value == BottomNavType.HOME,
            onClick = {
                homeScreenState.value = BottomNavType.HOME
                animate = false
            },
            label = {
                androidx.compose.material3.Text(
                    text = stringResource(id = R.string.navigation_item_home),
                    style = TextStyle(fontSize = 12.sp)
                )
            },
            modifier = Modifier.testTag(TestTags.BOTTOM_NAV_HOME_TEST_TAG)
        )
        NavigationBarItem(
            icon = {
                FaIcon(
                    faIcon = FaIcons.Tools, tint = androidx.compose.material3.LocalContentColor
                        .current.copy(
                            alpha =
                            LocalContentAlpha.current
                        )
                )
            },
            selected = homeScreenState.value == BottomNavType.FOOD,
            onClick = {
                homeScreenState.value = BottomNavType.FOOD
                animate = false
            },
            label = {
                androidx.compose.material3.Text(
                    text = stringResource(id = R.string.navigation_item_food),
                    style = TextStyle(fontSize = 12.sp)
                )
            },
            modifier = Modifier.testTag(TestTags.BOTTOM_NAV_WIDGETS_TEST_TAG)
        )
        NavigationBarItem(
            icon = {
                FaIcon(
                    faIcon = FaIcons.Tools, tint = androidx.compose.material3.LocalContentColor
                        .current.copy(
                            alpha =
                            LocalContentAlpha.current
                        )
                )
            },
            selected = homeScreenState.value == BottomNavType.LIST,
            onClick = {
                homeScreenState.value = BottomNavType.LIST
                animate = false
            },
            label = {
                androidx.compose.material3.Text(
                    text = stringResource(id = R.string.navigation_item_list),
                    style = TextStyle(fontSize = 12.sp)
                )
            },
            modifier = Modifier.testTag(TestTags.BOTTOM_NAV_WIDGETS_TEST_TAG)
        )
        NavigationBarItem(
            icon = {
                FaIcon(
                    faIcon = FaIcons.Tools, tint = androidx.compose.material3.LocalContentColor
                        .current.copy(
                            alpha =
                            LocalContentAlpha.current
                        )
                )
            },
            selected = homeScreenState.value == BottomNavType.SETTING,
            onClick = {
                homeScreenState.value = BottomNavType.SETTING
                animate = false
            },
            label = {
                androidx.compose.material3.Text(
                    text = stringResource(id = R.string.navigation_item_setting),
                    style = TextStyle(fontSize = 12.sp)
                )
            },
            modifier = Modifier.testTag(TestTags.BOTTOM_NAV_WIDGETS_TEST_TAG)
        )
    }
}

@Composable
private fun NavigationRailContent(
    modifier: Modifier,
    homeScreenState: MutableState<BottomNavType>,
) {
    var animate by remember { mutableStateOf(false) }
    NavigationRail(
        modifier = modifier,
    ) {
        NavigationRailItem(
            icon = {
                FaIcon(
                    faIcon = FaIcons.Home,
                    tint = androidx.compose.material3.LocalContentColor.current.copy(alpha = LocalContentAlpha.current)
                )
            },
            selected = homeScreenState.value == BottomNavType.HOME,
            onClick = {
                homeScreenState.value = BottomNavType.HOME
                animate = false
            },
            label = {
                 Text(
                    text = stringResource(id = R.string.navigation_item_home),
                    style = TextStyle(fontSize = 12.sp)
                )
            },
            modifier = Modifier.testTag(TestTags.BOTTOM_NAV_HOME_TEST_TAG)
        )
        NavigationRailItem(
            icon = {
                FaIcon(
                    faIcon = FaIcons.Tools, tint = androidx.compose.material3.LocalContentColor
                        .current.copy(
                            alpha =
                            LocalContentAlpha.current
                        )
                )
            },
            selected = homeScreenState.value == BottomNavType.FOOD,
            onClick = {
                homeScreenState.value = BottomNavType.FOOD
                animate = false
            },
            label = {
                Text(
                    text = stringResource(id = R.string.navigation_item_food),
                    style = TextStyle(fontSize = 12.sp)
                )
            },
            modifier = Modifier.testTag(TestTags.BOTTOM_NAV_WIDGETS_TEST_TAG)
        )
        NavigationRailItem(
            icon = {
                FaIcon(
                    faIcon = FaIcons.Tools, tint = androidx.compose.material3.LocalContentColor
                        .current.copy(
                            alpha =
                            LocalContentAlpha.current
                        )
                )
            },
            selected = homeScreenState.value == BottomNavType.LIST,
            onClick = {
                homeScreenState.value = BottomNavType.LIST
                animate = false
            },
            label = {
                Text(
                    text = stringResource(id = R.string.navigation_item_list),
                    style = TextStyle(fontSize = 12.sp)
                )
            },
            modifier = Modifier.testTag(TestTags.BOTTOM_NAV_WIDGETS_TEST_TAG)
        )
        NavigationRailItem(
            icon = {
                FaIcon(
                    faIcon = FaIcons.Tools, tint = androidx.compose.material3.LocalContentColor
                        .current.copy(
                            alpha =
                            LocalContentAlpha.current
                        )
                )
            },
            selected = homeScreenState.value == BottomNavType.SETTING,
            onClick = {
                homeScreenState.value = BottomNavType.SETTING
                animate = false
            },
            label = {
                Text(
                    text = stringResource(id = R.string.navigation_item_setting),
                    style = TextStyle(fontSize = 12.sp)
                )
            },
            modifier = Modifier.testTag(TestTags.BOTTOM_NAV_WIDGETS_TEST_TAG)
        )
    }
}

@OptIn(ExperimentalAnimationApi::class,
    ExperimentalFoundationApi::class,
    ExperimentalMaterialApi::class)
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    val appThemeState = remember { mutableStateOf(AppThemeState(false, ColorPallet.GREEN)) }
    BaseView(appThemeState.value, null) {
        MainAppContent(appThemeState)
    }
}