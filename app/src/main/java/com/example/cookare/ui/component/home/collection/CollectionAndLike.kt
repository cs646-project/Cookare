package com.example.cookare.ui.component.home.collection

import RecipeCard
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cookare.R
import com.example.cookare.ui.theme.CookareTheme
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

enum class TabPage {
    Collection, Like
}

/**
 * Shows the entire screen
 */
@OptIn(ExperimentalPagerApi::class)
@Composable
fun CollectionAndLikeScreen(defaultPage: TabPage){

    // cur selected tab (default page)
    var tabPage by remember { mutableStateOf(defaultPage) }
    val pagerState = rememberPagerState(
        pageCount = 2,
        initialPageOffset = if(defaultPage == TabPage.Collection) 0.0f else 1.0f
    )
    Scaffold(
        topBar = {
            TabBar(
                backgroundColor = androidx.compose.material3.MaterialTheme.colorScheme.onPrimaryContainer,
                tabPage = tabPage,
                onTabSelected = { tabPage = it },
                pagerState = pagerState
            )
        }
    ) {
        TabsContent(pagerState = pagerState)
    }
}

/**
 * Shows different content based on different tabs
 *
 */
@ExperimentalPagerApi
@Composable
fun TabsContent(
    pagerState: PagerState,
) {
    // horizontal pager for our tab layout
    HorizontalPager(state = pagerState) {
        // the different pages
            page ->
        when (page) {
            0 -> TabContentScreen(content = "Collection")
            1 -> TabContentScreen(content = "Like")
        }
    }
}

/**
 * Shows different content screens
 *
 */
@Composable
fun TabContentScreen(content: String) {
    Column(
        modifier = Modifier.fillMaxSize(),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
    ) {
        Column(){
            Text(
                text = content,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
            Row(){
                RecipeCard(title = "test1", description = "This is a test1", {})
                RecipeCard(title = "test2", description = "This is a test2", {})
            }
        }
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
 * Shows a composable tabs, can hold more Tab, also connect to TabContent
 *
 * @param tabPage -> cur selected tab page
 * @param onTabSelected -> called when tab is switched
 */
@OptIn(ExperimentalPagerApi::class)
@Composable
fun TabBar(
    backgroundColor: Color,
    tabPage: TabPage,
    onTabSelected: (tabPage: TabPage) -> Unit,
    pagerState: PagerState
) {
    val list = listOf(TabPage.Collection, TabPage.Like)
    val scope = rememberCoroutineScope()
    TabRow(
        selectedTabIndex = tabPage.ordinal,
        backgroundColor = backgroundColor,
        // this indicator will be forced to fill up the entire TabRow
        indicator = { tabPositions ->
            TabIndicator(tabPositions, tabPage)
        }
    ) {
        list.forEachIndexed { index, _ ->
            Tab(
                icon =
                if(list[index] == TabPage.Collection){
                    // if collection selected, solid, otherwise border
                    if(list[index] == tabPage) Icons.Default.Star else Icons.Default.StarBorder
                }
                else {
                    if(list[index] == tabPage) Icons.Default.Favorite else Icons.Default.FavoriteBorder
                },

                // use list[index] to tell from two pages
                title = if(list[index] == TabPage.Collection) stringResource(id = R.string.collection) else stringResource(id = R.string.like),
                selected = list[index] == tabPage,
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                    onTabSelected( if(list[index] == TabPage.Collection) TabPage.Collection else TabPage.Like)
                }
            )
        }
    }
}


/**
 *
 * Shows an indicator for the tab
 *
 * @param tabPositions -> the list of tab positions from a tab row
 */
@OptIn(ExperimentalPagerApi::class)
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
            spring(stiffness = Spring.StiffnessHigh)
        },
        label = "indicator left"
    ) {
        tabPositions[(tabPage.ordinal+1)%2].left
    }

    // indicatorRight is the horizontal position of the right edge of the indicator
    val indicatorRight by transition.animateDp(
        transitionSpec = {
            spring(stiffness = Spring.StiffnessHigh)
        },
        label = "indicator right"
    ) {
            tabPositions[(tabPage.ordinal+1)%2].right
    }

    Box(
        Modifier
            .fillMaxSize()
            .wrapContentSize(align = Alignment.BottomStart)
            .offset(x = indicatorLeft)
            .width(indicatorRight - indicatorLeft)
            .fillMaxSize()
            .border(
                BorderStroke(2.dp, colorResource(id = R.color.unselected_color)),
            )

    )
}

@OptIn(ExperimentalPagerApi::class)
@Preview
@Composable
private fun PriewTabBar() {
    CookareTheme() {
        val pagerState = PagerState(pageCount = 2)
        TabBar(
            backgroundColor = Color.DarkGray,
            tabPage = TabPage.Collection,
            pagerState = pagerState,
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
        CollectionAndLikeScreen(TabPage.Like)
    }
}
