package com.example.cookare.ui.component.list.collection

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
import androidx.compose.ui.text.style.TextAlign
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
fun ShowTabBar(){

    // cur selected tab
    var tabPage by remember { mutableStateOf(TabPage.Like) }
    val pagerState = rememberPagerState(pageCount = 2)
    Scaffold(
        topBar = {
            TabBar(
                backgroundColor = colorResource(id = R.color.tab_bar_color),
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
fun TabsContent(pagerState: PagerState) {
    // horizontal pager for our tab layout.
    HorizontalPager(state = pagerState) {
        // the different pages.
            page ->
        when (page) {
            0 -> TabContentScreen(content = "Page 1")
            1 -> TabContentScreen(content = "Page 2")
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
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = content,
            style = MaterialTheme.typography.h2,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
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
    // // a variable for the scope
    val scope = rememberCoroutineScope()
    TabRow(
        selectedTabIndex = tabPage.ordinal,
//        selectedTabIndex =  pagerState.currentPage,
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
                    if(pagerState.currentPage == index) Icons.Default.Star else Icons.Default.StarBorder
                }
                else {
                    if(pagerState.currentPage == index) Icons.Default.Favorite else Icons.Default.FavoriteBorder
                },

                // use list[index] to tell from two pages
                title = if(list[index] == TabPage.Collection) stringResource(id = R.string.collection) else stringResource(id = R.string.like),
                selected = pagerState.currentPage == index,
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                    // list[pagerState.currentPage] -> cur selected page
                    onTabSelected( if(list[pagerState.currentPage] == TabPage.Collection) TabPage.Collection else TabPage.Like)
                }
            )
        }

//        Tab(
//            icon = if( tabPage == TabPage.Collection) Icons.Default.Star else Icons.Default.StarBorder,
//            title = stringResource(id = R.string.collection),
//            selected = tabPage == TabPage.Collection,
//            onClick = { onTabSelected(TabPage.Collection) }
//        )
//        Tab(
//            icon = if( tabPage == TabPage.Like) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
//            title = stringResource(id = R.string.like),
//            selected = tabPage == TabPage.Like,
//            onClick = { onTabSelected(TabPage.Like) },
//        )
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
        ShowTabBar()
    }
}
