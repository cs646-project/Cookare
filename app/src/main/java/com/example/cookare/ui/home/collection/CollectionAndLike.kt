package com.example.cookare.ui.home.collection

import android.provider.ContactsContract
import androidx.compose.animation.animateColor
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.outlined.*
import androidx.compose.material.icons.sharp.Favorite
import androidx.compose.material.icons.sharp.Star
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cookare.R
import com.example.cookare.ui.theme.CookareTheme
import kotlinx.coroutines.selects.selectUnbiased

/* TODO: 1. style TabBar
         2. show different content on different Tab
         3. create component of post
         4. notification
         5. try to use Navigation
 */


enum class TabPage {
    Collection, Like
}

/**
 * Shows the entire screen
 */
@Composable
fun ShowTabBar(){

    // cur selected tab
    var tabPage by remember { mutableStateOf(TabPage.Collection) }
    Scaffold(
        topBar = {
            TabBar(
                backgroundColor = colorResource(id = R.color.tab_bar_color),
                tabPage = tabPage,
                onTabSelected = { tabPage = it } )
        }
    ) {

    }
}

/**
 * Shows a tab
 *
 * @param icon -> the icon to be shown on this tab
 * @param title -> the title to be shown on this tab
 * @param onClick -> called when this tab is clicked
 * @param selected -> if this tab page is selected
 * @param modifier -> Modifier
 */
@Composable
fun Tab(
    icon: ImageVector,
    title: String,
    onClick: () -> Unit,
    selected: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(60.dp)
            .clickable(onClick = onClick),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            tint = if(selected) Color.White else colorResource(id = R.color.unselected_color),
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = title,
            color = if(selected) Color.White else colorResource(id = R.color.unselected_color),
            fontWeight = if(selected) FontWeight.Bold else FontWeight.Normal
        )
    }
}

/**
 *
 * Shows a composable tabs, can hold more Tab
 *
 * @param tabPage -> cur selected tab page
 * @param onTabSelected -> called when tab is switched
 */
@Composable
fun TabBar(
    backgroundColor: Color,
    tabPage: TabPage,
    onTabSelected: (tabPage: TabPage) -> Unit
) {
    TabRow(
        selectedTabIndex = tabPage.ordinal,
        backgroundColor = backgroundColor,
        // this indicator will be forced to fill up the entire TabRow
        indicator = { tabPositions ->
            TabIndicator(tabPositions, tabPage)
        }
    ) {
        Tab(
            icon = if( tabPage == TabPage.Collection) Icons.Default.Star else Icons.Default.StarBorder,
            title = stringResource(id = R.string.collection),
            selected = tabPage == TabPage.Collection,
            onClick = { onTabSelected(TabPage.Collection) }
        )
        Tab(
            icon = if( tabPage == TabPage.Like) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
            title = stringResource(id = R.string.like),
            selected = tabPage == TabPage.Like,
            onClick = { onTabSelected(TabPage.Like) },
        )
    }
}


/**
 *
 * Shows an indicator for the tab
 *
 * @param tabPositions -> the list of tab positions from a tab row
 * @param tabPage -> cur selected tab page
 */
@Composable
fun TabIndicator(
    tabPositions: List<TabPosition>,
    tabPage: TabPage
) {
    val transition = updateTransition(
        targetState = tabPage,
        label = "Tab indicator"
    )

    // indicatorLeft is the horizontal position of the left edge of the indicator in the tab row
    val indicatorLeft by transition.animateDp(
        transitionSpec = {
            spring(stiffness = Spring.StiffnessMedium)
        },
        label = "indicator left"
    ) {
        page -> tabPositions[page.ordinal].left
    }

    // indicatorRight is the horizontal position of the right edge of the indicator
    val indicatorRight by transition.animateDp(
        transitionSpec = {
            spring(stiffness = Spring.StiffnessMedium)
        },
        label = "indicator right"
    ) {
            page -> tabPositions[page.ordinal].right
    }

    Box(
        Modifier
            .fillMaxSize()
            .wrapContentSize(align = Alignment.BottomStart)
            .offset(x = indicatorLeft)
            .width(indicatorRight - indicatorLeft)
            .fillMaxSize()
            .border(
                BorderStroke(2.dp, Color.White),
            )

    )
}

@Preview
@Composable
private fun PriewTabBar() {
    CookareTheme() {
        TabBar(
            backgroundColor = Color.DarkGray,
            tabPage = TabPage.Collection,
            onTabSelected = {}
        )
    }
}


@Preview
@Composable
private fun PreviewTab() {
    CookareTheme {
        Tab(
            icon = Icons.Default.Add,
            title = "test",
            selected = true,
            onClick = {}
        )
    }
}

@Preview
@Composable
private fun PreviewShowTabBar(){
    CookareTheme {
        ShowTabBar()
    }
}

