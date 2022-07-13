package com.example.cookare.ui.home

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.cookare.R
import com.example.cookare.model.*
import com.example.cookare.ui.theme.*
import com.example.cookare.ui.utils.ScreenRoute
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.example.cookare.ui.components.TopBar
import com.example.cookare.ui.components.CookareDivider
import kotlinx.coroutines.launch

var currentLove: Love? by mutableStateOf(null)
var currentLovePageState by mutableStateOf(LovePageState.Closed)
var cardSize by mutableStateOf(IntSize(0, 0))
var fullSize by mutableStateOf(IntSize(0, 0))
var cardOffset by mutableStateOf(IntOffset(0, 0))
enum class LovePageState {
    Closing, Closed, Opening, Open
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun HomeScreen(
    navController:NavController
) {
    val pagerState = rememberPagerState(pageCount = 4)

    Scaffold(

        floatingActionButton = {
            if (currentLovePageState==LovePageState.Closed ) {
                FloatingActionButton(
                    backgroundColor = green000,

                    onClick = { navController.navigate(ScreenRoute.PostTemplates.route) }) {
                    /* FAB content */
                    Icon(
                        painter = painterResource(R.drawable.ic_add),
                        contentDescription = "add_posts",
                        modifier = Modifier
                            .padding(10.dp)
                            .size(32.dp),
                        tint = BackgroundWhite
                    )
                }
            }
        },
        isFloatingActionButtonDocked = true,

        ) {

        Column(Modifier.onSizeChanged { fullSize = it }) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(BackgroundWhite)

            ) {
                TopBar(users =users,navController=navController)
                //SearchBar()
                CookareDivider()
                Row(modifier = Modifier.padding(start = 20.dp), verticalAlignment = Alignment.CenterVertically){
                    Text("Recipe", fontSize = 18.sp, color = Gray100, fontWeight = FontWeight.Bold)
                    NamesBar(
                        pagerState = pagerState,
                    )
                }

                CommunityContent(pagerState = pagerState)


            }

        }
        LoveDetailsPage(currentLove, currentLovePageState, cardSize, fullSize, cardOffset, {
            currentLovePageState = LovePageState.Closing
        }, {
            currentLovePageState = LovePageState.Closed
        })
    }

}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun NamesBar(pagerState: PagerState) {
    val names = listOf("All", "Veggie","Meat","Sweet")

    TabRow(

        modifier = Modifier.padding(12.dp,0.dp),
        backgroundColor = BackgroundWhite,
        selectedTabIndex = pagerState.currentPage,
        indicator ={ positions ->
            TabRowDefaults.Indicator(
                Modifier.tabIndicatorOffset(positions[pagerState.currentPage])
                ,
                color = green000
            )
        },
        divider = {}
    ) {
        val scope = rememberCoroutineScope()
        names.forEachIndexed{
                index, _ ->
            Tab(selected = index == pagerState.currentPage,
                onClick = {scope.launch {pagerState.scrollToPage(index)}},
                text = {Text(
                    names[index], fontSize = 12.sp,
                    color = if (index == pagerState.currentPage) green000 else Gray
                )}
            )
        }
    }
}

@ExperimentalPagerApi
@Composable
fun CommunityContent(
    pagerState: PagerState,
) {
    // horizontal pager for our tab layout
    HorizontalPager(state = pagerState) {
        // the different pages
            page ->
        when (page) {
            0 -> RDContent(loves)
            1 -> RDContent(loves1)
            2 -> RDContent(loves2)
            3 -> RDContent(loves3)
        }
    }
}

@Composable
fun RDContent(loves:List<Love>){

    LovesArea(
        { cardSize = it },
        { love, offset ->
            currentLove = love
            currentLovePageState = LovePageState.Opening
            cardOffset = offset
        },
        loves=loves)

}

@Composable
fun LovesArea(onCardSizedChanged: (IntSize) -> Unit,
              onCardClicked: (love: Love, offset: IntOffset) -> Unit,loves:List<Love>) {
    LazyVerticalGrid(columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    )

    {
        items(loves.size) { index ->

            var intOffset: IntOffset? by remember { mutableStateOf(null) }
            androidx.compose.material3.Button(onClick = { onCardClicked(loves[index], intOffset!!) },
                Modifier
                    .width(220.dp)
                    .onSizeChanged { if (index == 0) onCardSizedChanged(it) }
                    .onGloballyPositioned {
                        val offset = it.localToRoot(Offset(0f, 0f))
                        intOffset = IntOffset(offset.x.toInt(), offset.y.toInt())
                    },
                shape = RoundedCornerShape(16.dp),
                contentPadding = PaddingValues(6.dp),
                colors = androidx.compose.material3.ButtonDefaults.outlinedButtonColors(
                    containerColor = Color.White
                )
            ) {
                Column {
                  /*  Image(
                        painterResource(loves[index].cover_url), "图像",
                        Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .fillMaxWidth()
                            .aspectRatio(1.35f),
                        contentScale = ContentScale.Crop,
                        alignment = Alignment.Center
                    )*/
                    AsyncImage(
                        model = loves[index].cover_url,
                        contentDescription = "cover",
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .fillMaxWidth()
                            .aspectRatio(1.35f),
                        contentScale = ContentScale.Crop,
                        alignment = Alignment.Center
                    )
                    Row(
                        Modifier.padding(8.dp, 12.dp, 8.dp, 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column() {
                            androidx.compose.material3.Text(
                                loves[index].title,
                                color = Color.Black,
                                fontSize = 15.sp
                            )
                            Spacer(Modifier.height(4.dp))
                            androidx.compose.material3.Text(
                                loves[index].tags,
                                color =Gray,
                                fontSize = 14.sp
                            )
                        }
                        Spacer(Modifier.weight(1f))
                        Row(
                            Modifier
                                .clip(RoundedCornerShape(10.dp))
                                .background(green100)
                                .padding(6.dp, 11.dp, 8.dp, 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            androidx.compose.material3.Icon(
                                painterResource(R.drawable.ic_cart), "", Modifier.size(24.dp),
                                tint = green000
                            )

                        }
                    }
                }
            }


        }
    }



}
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun LoveDetailsPage(
    love: Love?,
    pageState: LovePageState,
    cardSize: IntSize,
    fullSize: IntSize,
    cardOffset: IntOffset,
    onPageClosing: () -> Unit,
    onPageClosed: () -> Unit
) {
    var animReady by remember { mutableStateOf(false) }
    val background by animateColorAsState(
        if (pageState > LovePageState.Closed) Color(0xfff8f8f8) else Color.White,
        finishedListener = {
            if (pageState == LovePageState.Closing) {
                onPageClosed()
                animReady = false
            }
        })
    val cornerSize by animateDpAsState(if (pageState > LovePageState.Closed) 0.dp else 16.dp)
    val paddingSize by animateDpAsState(if (pageState > LovePageState.Closed) 10.dp else 6.dp)
    val size by animateIntSizeAsState(if (pageState > LovePageState.Closed) fullSize else cardSize)
    val titleOuterPaddingHorizontal by animateDpAsState(if (pageState > LovePageState.Closed) 14.dp else 0.dp)
    val titlePaddingHorizontal by animateDpAsState(if (pageState > LovePageState.Closed) 16.dp else 8.dp)
    val titlePaddingTop by animateDpAsState(if (pageState > LovePageState.Closed) 18.dp else 12.dp)
    val titlePaddingBottom by animateDpAsState(if (pageState > LovePageState.Closed) 16.dp else 8.dp)
    val titleOffsetY by animateDpAsState(if (pageState > LovePageState.Closed) (-40).dp else 0.dp)
    val titleFontSize by animateFloatAsState(if (pageState > LovePageState.Closed) 18f else 15f)
    val titleFontWeight by animateIntAsState(if (pageState > LovePageState.Closed) 900 else 700)
    val titleSpacing by animateDpAsState(if (pageState > LovePageState.Closed) 10.dp else 4.dp)
    val subtitleFontSize by animateFloatAsState(if (pageState > LovePageState.Closed) 15f else 14f)
    val badgeCornerSize by animateDpAsState(if (pageState > LovePageState.Closed) 15.dp else 10.dp)
    val badgeWidth by animateDpAsState(if (pageState > LovePageState.Closed) 90.dp else 0.dp)
    val badgeHeight by animateDpAsState(if (pageState > LovePageState.Closed) 66.dp else 0.dp)
    val badgeBackground by animateColorAsState(
        if (pageState > LovePageState.Closed) green000 else Gray100

    )
    val badgeContentColor by animateColorAsState(
        if (pageState > LovePageState.Closed) Color.White else green000
    )

    val imageCornerSize by animateDpAsState(if (pageState > LovePageState.Closed) 32.dp else 16.dp)
    val imageRatio by animateFloatAsState(if (pageState > LovePageState.Closed) 1f else 1.35f)
    val fullOffset = remember { IntOffset(0, 0) }
    val offsetAnimatable = remember { Animatable(IntOffset(0, 0), IntOffset.VectorConverter) }
    LaunchedEffect(pageState) {
        when (pageState) {
            LovePageState.Opening -> {
                animReady = true
                offsetAnimatable.snapTo(cardOffset)
                offsetAnimatable.animateTo(fullOffset)
            }
            LovePageState.Closing -> {
                offsetAnimatable.snapTo(fullOffset)
                offsetAnimatable.animateTo(cardOffset)
            }
            else -> {}
        }
    }
    if (pageState != LovePageState.Closed && animReady) {
        Box(
            Modifier
                .offset { offsetAnimatable.value }
                .clip(RoundedCornerShape(cornerSize))
                .width(with(LocalDensity.current) { size.width.toDp() })
                .height(with(LocalDensity.current) { size.height.toDp() })
                .background(background)
                .padding(paddingSize)
        ) {
            Column(Modifier.verticalScroll(rememberScrollState())) {
                /*Image(
                    painterResource(love!!.imageId),
                    "image",
                    Modifier
                        .clip(RoundedCornerShape(imageCornerSize))
                        .fillMaxWidth()
                        .aspectRatio(imageRatio),
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.Center
                )*/
                AsyncImage(
                    model = love!!.cover_url,
                    contentDescription = "cover",
                    modifier = Modifier
                        .clip(RoundedCornerShape(imageCornerSize))
                        .fillMaxWidth()
                        .aspectRatio(imageRatio),
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.Center
                )
                Row(
                    Modifier
                        .offset(0.dp, titleOffsetY)
                        .padding(titleOuterPaddingHorizontal, 0.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.White)
                        .padding(
                            titlePaddingHorizontal,
                            titlePaddingTop,
                            titlePaddingHorizontal,
                            titlePaddingBottom
                        ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column() {
                        androidx.compose.material3.Text(
                            love.title,
                            color = Color.Black,
                            fontSize = titleFontSize.sp,
                            fontWeight = FontWeight(titleFontWeight)
                        )
                        Spacer(Modifier.height(titleSpacing))
                        androidx.compose.material3.Text(
                            love.tags,
                            color =Gray,
                            fontSize = subtitleFontSize.sp
                        )
                    }
                    Spacer(Modifier.weight(1f))

                    androidx.compose.material3.Button(
                        onClick = { },


                    ) {
                        androidx.compose.material3.Text(
                            "Add into ",

                            color = badgeContentColor,
                            fontSize = 14.sp
                        )
                        androidx.compose.material3.Icon(
                            painterResource(R.drawable.ic_cart),
                            "",
                            Modifier.size(24.dp),
                            tint = badgeContentColor
                        )
                    }



                   }
                androidx.compose.material3.Text(
                    "Ingredients",
                    Modifier
                        .offset(0.dp, titleOffsetY)
                        .padding(14.dp, 24.dp, 14.dp, 14.dp),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

                androidx.compose.material3.Text(
                    love.ingredients,
                    Modifier
                        .offset(0.dp, titleOffsetY)
                        .padding(14.dp, 0.dp), fontSize = 15.sp, color = Gray
                )
                androidx.compose.material3.Text(
                    "Some Tips",
                    Modifier
                        .offset(0.dp, titleOffsetY)
                        .padding(14.dp, 24.dp, 14.dp, 14.dp),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

                androidx.compose.material3.Text(
                    love.description,
                    Modifier
                        .offset(0.dp, titleOffsetY)
                        .padding(14.dp, 0.dp), fontSize = 15.sp, color = Gray
                )

            }
            Surface(
                { onPageClosing() },
                Modifier.padding(14.dp, 32.dp),
                color = Color.White,
                shape = CircleShape,
                indication = rememberRipple()
            ) {
                androidx.compose.material3.Icon(
                    painterResource(R.drawable.ic_back),
                    "return",
                    Modifier
                        .padding(8.dp)
                        .size(26.dp), tint = Color.Black
                )
            }
            androidx.compose.material3.Text(
                "Detail",
                Modifier
                    .align(Alignment.TopCenter)
                    .padding(44.dp),
                color = Color.White,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold
            )
            Surface(
                { },
                Modifier
                    .align(Alignment.TopEnd)
                    .padding(14.dp, 32.dp),
                color = Color.White,
                shape = CircleShape,
                indication = rememberRipple()
            ) {
                androidx.compose.material3.Icon(
                    painterResource(R.drawable.ic_delete),
                    "delete",
                    Modifier
                        .padding(8.dp)
                        .size(26.dp), tint = Color.Black
                )
            }
        }
    }
}




/*
@Composable
fun TopBar(
    navController:NavController
) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(28.dp, 28.dp, 28.dp, 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedButton(
            onClick = {},
            modifier= Modifier.size(64.dp),
            shape = CircleShape,
            contentPadding = PaddingValues(0.dp),
        ) {
            Image(painterResource(R.drawable.avatar_lia), "avatar",
                Modifier
                    .clip(CircleShape)
                    .fillMaxWidth())
        }
        Column(
            Modifier
                .padding(start = 14.dp)
                .weight(1f)) {
            Text("Welcome back！", fontSize = 18.sp, color = Gray100)
            Text("Karina", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }
        OutlinedButton(
            onClick = { navController.navigate(ScreenRoute.NotificationScreen.route) },
            modifier= Modifier.size(50.dp),
            shape = CircleShape,
            border= BorderStroke(5.dp, green100),
            contentPadding = PaddingValues(0.dp),
            colors = ButtonDefaults.buttonColors(green100)
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_notification_new),
                contentDescription = "notification",
                modifier = Modifier
                    .padding(10.dp)
                    .size(32.dp),
                tint = Color.Black
            )
        }
        Spacer(modifier = Modifier.padding(8.dp))
        OutlinedButton(
            onClick = {
                navController.navigate(ScreenRoute.LikeScreen.route)
            },
            modifier= Modifier.size(50.dp),
            shape = CircleShape,
            border= BorderStroke(5.dp, green100),
            contentPadding = PaddingValues(0.dp),
            colors = ButtonDefaults.buttonColors(green100)
        ) {
            Icon(
                imageVector = Icons.Rounded.FavoriteBorder,
                contentDescription = "like",
                modifier = Modifier
                    .padding(10.dp)
                    .size(22.dp),
                tint = Color.Black
            )
        }
    }
}*/

/*@Composable
fun SearchBar() {
    Row(
        Modifier
            .padding(24.dp, 2.dp, 24.dp, 6.dp)
            .fillMaxWidth()
            .height(56.dp)
            .clip(RoundedCornerShape(28.dp))
            .background(Color.White),
        verticalAlignment = Alignment.CenterVertically
    ) {
        var searchText by remember { mutableStateOf("") }
        BasicTextField(searchText, { searchText = it },
            Modifier
                .padding(start = 24.dp)
                .weight(1f),
            textStyle = TextStyle(fontSize = 15.sp)
        ) {
            if (searchText.isEmpty()) {
                Text("Search", color = Gray, fontSize = 15.sp)
            }
            it()
        }
        Box(
            Modifier
                .padding(6.dp)
                .fillMaxHeight()
                .aspectRatio(1f)
                .clip(CircleShape)
                .background(green000)
        ) {
            Icon(painterResource(R.drawable.ic_search), "Search",
                Modifier
                    .size(24.dp)
                    .align(Alignment.Center), tint = Color.White
            )
        }
    }
}*/

