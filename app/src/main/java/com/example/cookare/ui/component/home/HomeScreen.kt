package com.example.cookare.ui.component.home

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.cookare.ui.theme.*

import androidx.annotation.DrawableRes
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.runtime.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.cookare.R

import com.example.cookare.ui.theme.BackgroundWhite
import com.example.cookare.ui.theme.Gray
import com.example.cookare.ui.utils.ScreenRoute
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.*
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import com.example.cookare.BottomNavType
import com.example.cookare.ui.component.home.collection.LikeContent
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState


@OptIn(
    ExperimentalMaterialApi::class,
    ExperimentalAnimationApi::class,
    ExperimentalMaterialApi::class)

var currentLove: Love? by mutableStateOf(null)
var currentLovePageState by mutableStateOf(LovePageState.Closed)
var cardSize by mutableStateOf(IntSize(0, 0))
var fullSize by mutableStateOf(IntSize(0, 0))
var cardOffset by mutableStateOf(IntOffset(0, 0))

@OptIn(ExperimentalPagerApi::class)
@Composable
fun HomeScreen(
    navController:NavController,
    homeScreenState: MutableState<BottomNavType>
) {
    val pagerState = rememberPagerState(pageCount = 4)
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                backgroundColor = green000,

                onClick = { navController.navigate(ScreenRoute.PostTemplates.route) }) {
                /* FAB content */
                Icon(painter = painterResource(R.drawable.ic_add), contentDescription = "add_posts",
                    modifier = Modifier
                        .padding(10.dp)
                        .size(32.dp),
                tint = BackgroundWhite)
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
                TopBar(navController, homeScreenState)
                SearchBar()

                NamesBar(
                            pagerState = pagerState,
                        )
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

@Composable
fun TopBar(
    navController:NavController,
    homeScreenState: MutableState<BottomNavType>
) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(28.dp, 28.dp, 28.dp, 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedButton(
            onClick = { homeScreenState.value = BottomNavType.PROFILE
                navController.navigate(ScreenRoute.ProfileScreen.route) },
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
}

@Composable
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
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun NamesBar(pagerState: PagerState) {
    val names = listOf("New for you", "Vegetarian","Lactose free","Gluten free")

    TabRow(

        modifier = Modifier.padding(12.dp, 8.dp),
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
        names.forEachIndexed{
            index, _ ->
            Column(
                Modifier
                    .background(BackgroundWhite)
                    .width(IntrinsicSize.Max)
                    .padding(bottom = 4.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,) {
                Text(names[index], fontSize = 15.sp,
                    color = if (index == pagerState.currentPage) green000 else Gray
                )
    //                Box(
    //                    Modifier
    //                        .fillMaxWidth()
    //                        .padding(top = 4.dp)
    //                        .height(2.dp)
    //                        .clip(RoundedCornerShape(1.dp))
    //                        .background(
    //                            if (index == pagerState.currentPage) green000 else Color.Transparent
    //                        )
    //                )
            }
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
                    Image(
                        painterResource(loves[index].imageId), "图像",
                        Modifier
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
                                loves[index].name,
                                color = Color.Black,
                                fontSize = 15.sp
                            )
                            Spacer(Modifier.height(4.dp))
                            androidx.compose.material3.Text(
                                loves[index].category,
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
                                painterResource(R.drawable.ic_star), "", Modifier.size(24.dp),
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
                Image(
                    painterResource(love!!.imageId),
                    "image",
                    Modifier
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
                            love.name,
                            color = Color.Black,
                            fontSize = titleFontSize.sp,
                            fontWeight = FontWeight(titleFontWeight)
                        )
                        Spacer(Modifier.height(titleSpacing))
                        androidx.compose.material3.Text(
                            love.category,
                            color =Gray,
                            fontSize = subtitleFontSize.sp
                        )
                    }
                    Spacer(Modifier.weight(1f))
                    Box(
                        Modifier
                            .width(badgeWidth)
                            .height(badgeHeight)
                            .clip(RoundedCornerShape(badgeCornerSize))
                            .background(badgeBackground)
                            .padding(6.dp, 11.dp, 8.dp, 8.dp)
                    ) {
                        androidx.compose.material3.Text(
                            love.scoreText,
                            Modifier.align(Alignment.TopCenter),
                            color = badgeContentColor,
                            fontSize = 14.sp
                        )
                        Row(
                            Modifier.align(Alignment.BottomCenter),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            androidx.compose.material3.Icon(
                                painterResource(R.drawable.ic_star),
                                "",
                                Modifier.size(24.dp),
                                tint = badgeContentColor
                            )
                            androidx.compose.material3.Text(
                                love.score.toString(),
                                color = badgeContentColor,
                                fontSize = 14.sp
                            )
                        }
                    }
                }
                androidx.compose.material3.Text(
                    "The detail ",
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
                    "返回",
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
                fontSize = 16.sp,
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
                    painterResource(R.drawable.ic_more),
                    "更多",
                    Modifier
                        .padding(8.dp)
                        .size(26.dp), tint = Color.Black
                )
            }
        }
    }
}

data class Love(
    val name: String,
    val category: String,
    val score: Float,
    val scoreText: String,
    @DrawableRes val imageId: Int,
    val description: String
)
val loves = mutableStateListOf(
    Love("Roasted Chicken.", "recommendation", 4.4f, "favorite", R.drawable.ic_pic1, "One Pan Garlic Roasted Chicken and Baby Potatoes. One pan garlic roasted chicken and baby potatoes is an easy to make, delicious, and wholesome meal for the entire family. Prep this sheet pan in 10 minutes. and a bright kick of white wine. It’s the kind of meal you can look forward to making and eating!"),
    Love("Green egg", "recommendation", 4.8f, "very good", R.drawable.ic_pic3, "This White Bean Chicken Soup is a slurpable, soothing, chicken broth-based soup, that's filled with white beans, roasted chicken, savory herbs, and hearty root vegetables. It's craveable on cold and rainy days."),
    Love("Tomato fish", "recommendation", 4.8f, "very good", R.drawable.ic_pic2, "This White Bean Chicken Soup is a slurpable, soothing, chicken broth-based soup, that's filled with white beans, roasted chicken, savory herbs, and hearty root vegetables. It's craveable on cold and rainy days."),

    Love(
        "Tomato pasta",
        "recommendation",
        5f,
        "favorite",
        R.drawable.ic_pic31,
        """
     This White Bean Chicken Soup is a slurpable, soothing, chicken broth-based soup, that's filled with white beans, roasted chicken, savory herbs, and hearty root vegetables. It's craveable on cold and rainy days.
     
    """.trimIndent()
    )
)
val loves1 = mutableStateListOf(
    Love("Garlic Shrimp", "vegetarian", 4.4f, "favorite", R.drawable.ic_pic_21, "Suffice it to say, I’m currently recovering from a fried food hangover! As I type I’m sipping on a green juice and dreaming of this healthy, flavorful pan-seared cod I made last weekend. It took about 40 minutes start-to-finish and is exploding with flavor thanks to fresh basil, juicy tomatoes, plenty of garlic, and a bright kick of white wine. It’s the kind of meal you can look forward to making and eating!"),
    Love("Gluten noodle", "vegetarian", 4.8f, "very good", R.drawable.ic_pic22, "This White Bean Chicken Soup is a slurpable, soothing, chicken broth-based soup, that's filled with white beans, roasted chicken, savory herbs, and hearty root vegetables. It's craveable on cold and rainy days."),
    Love("vegetable soup", "vegetarian", 4.8f, "very good", R.drawable.ic_pic24, "Apple cider vinegar, potato, tomato paste, red pepper broth-based soup, that's filled with white beans, roasted chicken, savory herbs, and hearty root vegetables. It's craveable on cold and rainy days."),
    Love(
        "Fish Soup",
        "recommendation",
        5f,
        "favorite",
        R.drawable.ic_pic23,
        """
     Creamy Garlic Shrimp. Creamy garlic shrimp is a delicious, quick and easy 15-minute meal (including prep!) that you need to include in your weeknight dinner meal plan
    
    """.trimIndent()
    )
)
val loves2 = mutableStateListOf(
    Love("Garlic Shrimp", "Lactose free", 4.4f, "favorite", R.drawable.ic_pic1, "Suffice it to say, I’m currently recovering from a fried food hangover! As I type I’m sipping on a green juice and dreaming of this healthy, flavorful pan-seared cod I made last weekend. It took about 40 minutes start-to-finish and is exploding with flavor thanks to fresh basil, juicy tomatoes, plenty of garlic, and a bright kick of white wine. It’s the kind of meal you can look forward to making and eating!"),
    Love("Green egg", "Lactose free", 4.8f, "very good", R.drawable.ic_pic3, "This White Bean Chicken Soup is a slurpable, soothing, chicken broth-based soup, that's filled with white beans, roasted chicken, savory herbs, and hearty root vegetables. It's craveable on cold and rainy days."),
    Love(
        "Fish Soup",
        "recommendation",
        5f,
        "favorite",
        R.drawable.ic_pic2,
        """
    Creamy Tuscan Chicken. Creamy Tuscan chicken with sun-dried tomatoes and spinach is a quick and easy 30 minute Italian chicken dinner that is delicious, flavorful, and comforting.
    
    """.trimIndent()
    )
)
val loves3 = mutableStateListOf(
    Love("Glazed Honey Balsamic Pork Chops", "Gluten free", 4.4f, "favorite", R.drawable.ic_pic31, "Suffice it to say, I’m currently recovering from a fried food hangover! As I type I’m sipping on a green juice and dreaming of this healthy, flavorful pan-seared cod I made last weekend. It took about 40 minutes start-to-finish and is exploding with flavor thanks to fresh basil, juicy tomatoes, plenty of garlic, and a bright kick of white wine. It’s the kind of meal you can look forward to making and eating!"),
    Love("Tomato egg", "Gluten free", 4.8f, "very good", R.drawable.ic_pic45, "This White Bean Chicken Soup is a slurpable, soothing, chicken broth-based soup, that's filled with white beans, roasted chicken, savory herbs, and hearty root vegetables. It's craveable on cold and rainy days."),
    Love("Green egg", "Lactose free", 4.8f, "very good", R.drawable.ic_pic44, "It is a slurpable, soothing, chicken broth-based soup, that's filled with white beans, roasted chicken, savory herbs, and hearty root vegetables. It's craveable on cold and rainy days."),
    Love(
        "Fish Soup",
        "recommendation",
        5f,
        "favorite",
        R.drawable.ic_pic2,
        """
   Glazed Honey Balsamic Pork Chops. Quick and easy, glazed honey balsamic pork chops are tender and juicy, seared in thyme and coated with a honey balsamic sauce. Make it in under 30 minutes.
    """.trimIndent()
    )
)
enum class LovePageState {
    Closing, Closed, Opening, Open
}
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    LoveDetailsPage(currentLove, currentLovePageState, cardSize, fullSize, cardOffset, {
        currentLovePageState = LovePageState.Opening
    }, {
        currentLovePageState = LovePageState.Open
    })

}