package com.example.cookare.ui.home

import android.content.Context
import android.content.Intent
import android.util.Log
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.rememberImagePainter
import com.amplifyframework.core.Amplify
import com.example.cookare.R
import com.example.cookare.activities.EditPostActivity
import com.example.cookare.model.Data
import com.example.cookare.model.Recipe
import com.example.cookare.model.users
import com.example.cookare.ui.components.TopBar
import com.example.cookare.ui.theme.*
import com.example.cookare.ui.utils.ScreenRoute
import com.example.cookare.viewModels.PlanViewModel
import com.example.cookare.viewModels.PostRecipeViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch
import java.io.File


var currentLove: Data? by mutableStateOf(null)
var currentLovePageState by mutableStateOf(LovePageState.Closed)
var cardSize by mutableStateOf(IntSize(0, 0))
var fullSize by mutableStateOf(IntSize(0, 0))
var cardOffset by mutableStateOf(IntOffset(0, 0))


@OptIn(ExperimentalPagerApi::class)
@Composable
fun HomeScreen(
    navController: NavController,
    recipeViewModel: PostRecipeViewModel,
    planViewModel: PlanViewModel,
) {
    val pagerState = rememberPagerState(pageCount = 4)
    recipeViewModel.getAllRecipes()
    val data = recipeViewModel.resRecipeList.value

    Scaffold(
        floatingActionButton = {
            if (data.isNotEmpty()) {
                if (currentLovePageState == LovePageState.Closed) {
                    FloatingActionButton(
                        backgroundColor = green000,

                        onClick = { navController.navigate(ScreenRoute.PostTemplates.route) }) {
                        Icon(
                            painter = painterResource(R.drawable.ic_add),
                            contentDescription = "add_posts",
                            modifier = Modifier
                                .padding(10.dp)
                                .size(32.dp),
                            tint = BackgroundWhite
                        )
                    }
                } else {
//                    FloatingActionButton(
//                        backgroundColor = green000,
//
//                        onClick = { }) {
//                        Icon(
//                            painter = painterResource(R.drawable.ic_cart),
//                            contentDescription = "add_cart",
//                            modifier = Modifier
//                                .padding(10.dp)
//                                .size(32.dp),
//                            tint = BackgroundWhite
//                        )
//                    }
                }
            } else {
                currentLovePageState = LovePageState.Closed
                FloatingActionButton(
                    backgroundColor = green000,

                    onClick = {
                        navController.navigate(ScreenRoute.PostTemplates.route)
//                        navController.navigate(ScreenRoute.PostTemplates.route)
                    }) {
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
                TopBar(users, navController)
                Divider()
                Row(
                    modifier = Modifier.padding(start = 20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    androidx.compose.material.Text(
                        modifier = Modifier.padding(5.dp, 20.dp, 8.dp, 0.dp),
                        text = "My Recipe",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
//                    if (data.isNotEmpty()) {
//                        NamesBar(
//                            pagerState = pagerState,
//                        )
//                    }
                }
//                CommunityContent(pagerState = pagerState, hiltViewModel())
                if (data.isNotEmpty()) {
//                    CommunityContent(pagerState = pagerState, data, planViewModel)
                    RDContent(data = data, planViewModel = planViewModel)
                } else {
                    AsyncImage(
                        model = "https://i.postimg.cc/7Z8rbqTP/welcome.jpg",
                        contentDescription = "",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp)
                    )
                    AsyncImage(
                        model = "https://i.postimg.cc/QNmqHm9F/homepage.jpg",
                        contentDescription = "",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 10.dp)
                    )
//                    CommunityContent(pagerState = pagerState, defaultData, planViewModel)

                }
                //RDContent(recipes = recipes)
            }

        }
        if (data.isNotEmpty()) {
            currentLove?.let { it1 ->
                LoveDetailsPage(it1, currentLovePageState, cardSize, fullSize, cardOffset, {
                    currentLovePageState = LovePageState.Closing
                }, {
                    currentLovePageState = LovePageState.Closed
                },
                    navController,
                    recipeViewModel
                )
            }
        }
    }

}

/*
@Composable
fun TopBar(
    navController: NavController
) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(28.dp, 28.dp, 28.dp, 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedButton(
            onClick = {},
            modifier = Modifier.size(64.dp),
            shape = CircleShape,
            contentPadding = PaddingValues(0.dp),
        ) {
            Image(
                painterResource(R.drawable.avatar_lia), "avatar",
                Modifier
                    .clip(CircleShape)
                    .fillMaxWidth()
            )
        }
        Column(
            Modifier
                .padding(start = 14.dp)
                .weight(1f)
        ) {
            Text("Welcome back！", fontSize = 18.sp, color = Gray100)
            Text("Karina", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }
        OutlinedButton(
            onClick = { navController.navigate(ScreenRoute.NotificationScreen.route) },
            modifier = Modifier.size(50.dp),
            shape = CircleShape,
            border = BorderStroke(5.dp, green100),
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
            modifier = Modifier.size(50.dp),
            shape = CircleShape,
            border = BorderStroke(5.dp, green100),
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
        BasicTextField(
            searchText, { searchText = it },
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
            Icon(
                painterResource(R.drawable.ic_search), "Search",
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
    val names = listOf("All", "Veggie", "Meat", "Sweet")

    TabRow(

        modifier = Modifier.padding(12.dp, 0.dp),
        backgroundColor = BackgroundWhite,
        selectedTabIndex = pagerState.currentPage,
        indicator = { positions ->
            TabRowDefaults.Indicator(
                Modifier.tabIndicatorOffset(positions[pagerState.currentPage]),
                color = green000
            )
        },
        divider = {}
    ) {
        val scope = rememberCoroutineScope()
        names.forEachIndexed { index, _ ->
            Tab(selected = index == pagerState.currentPage,
                onClick = { scope.launch { pagerState.scrollToPage(index) } },
                text = {
                    Text(
                        names[index], fontSize = 12.sp,
                        color = if (index == pagerState.currentPage) green000 else Gray
                    )
                }
            )
        }
    }
}

@ExperimentalPagerApi
@Composable
fun CommunityContent(
    pagerState: PagerState,
//    viewModel: PostRecipeViewModel
    data: List<Data>,
    planViewModel: PlanViewModel
) {
//    val data = viewModel.resRecipeList.value

    HorizontalPager(state = pagerState) {
        // the different pages
            page ->
        when (page) {
            0 -> RDContent(data = data, planViewModel = planViewModel)
            1 -> RDContent(data = data, planViewModel = planViewModel)
            2 -> RDContent(data = data, planViewModel = planViewModel)
            3 -> RDContent(data = data, planViewModel = planViewModel)
        }
    }
}*/

@Composable
fun RDContent(data: List<Data>, planViewModel: PlanViewModel) {
    LovesArea(
        { cardSize = it },
        { data, offset ->
            currentLove = data
            currentLovePageState = LovePageState.Opening
            cardOffset = offset
        },
        data = data,
        planViewModel = planViewModel
    )
}


@Composable
fun LovesArea(
    onCardSizedChanged: (IntSize) -> Unit,
    onCardClicked: (data: Data, offset: IntOffset) -> Unit, data: List<Data>,
    planViewModel: PlanViewModel
) {

    val context = LocalContext.current
    val recipes = data.map { it.recipe }
    var showCartDialog by remember { mutableStateOf(false) }

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    )

    {
        items(recipes.size) { index ->

            var intOffset: IntOffset? by remember { mutableStateOf(null) }
            androidx.compose.material3.Button(onClick = {
                onCardClicked(
                    data[index],
                    intOffset!!
                )
            },
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
                    recipes[index]?.coverUrl?.let {
                        val downloadedImage = recipes[index].coverUrl?.let { it1 ->
                            downloadPhoto(
                                it1,
                                context
                            )
                        }
                        Image(
                            painter = rememberImagePainter(downloadedImage),
                            contentDescription = "Recipe Featured Image",
                            Modifier
                                .clip(RoundedCornerShape(16.dp))
                                .fillMaxWidth()
                                .aspectRatio(1.35f),
                            contentScale = ContentScale.Crop,
                            alignment = Alignment.Center
                        )
                    }

                    Row(
                        Modifier.padding(8.dp, 12.dp, 8.dp, 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column() {
                            recipes[index]?.title?.let {
                                androidx.compose.material3.Text(
                                    it,
                                    color = Color.Black,
                                    fontSize = 15.sp
                                )
                            }
                            Spacer(Modifier.height(4.dp))
                        }
                        Spacer(Modifier.weight(1f))
                        Row(
                            Modifier
                                .clip(RoundedCornerShape(10.dp))
                                .background(green100),
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            androidx.compose.material3.OutlinedButton(
                                onClick = {
                                    recipes[index].id?.let { planViewModel.updatePlan(it) }
                                    showCartDialog = true
                                          },
                                modifier = Modifier
                                    .size(40.dp)
                                    .padding(5.dp),
                                shape = CircleShape,
                                border = BorderStroke(1.5.dp, green100),
                                contentPadding = PaddingValues(0.dp),
                            ) {
                                androidx.compose.material3.Icon(
                                    painterResource(R.drawable.ic_cart), "", Modifier.size(24.dp),
                                    tint = green000,
                                )
                            }
                        }

                        if(showCartDialog){
                            androidx.compose.material3.AlertDialog(
                                onDismissRequest = {
                                    showCartDialog = false
                                },
                                text = {
                                    androidx.compose.material3.Text("Added it to list successfully!")
                                },
                                confirmButton = {
                                    androidx.compose.material3.Button(
                                        onClick = {
                                            showCartDialog = false
                                        }) {
                                        androidx.compose.material3.Text("OK")
                                    }
                                }
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
    data: Data,
    pageState: LovePageState,
    cardSize: IntSize,
    fullSize: IntSize,
    cardOffset: IntOffset,
    onPageClosing: () -> Unit,
    onPageClosed: () -> Unit,
    navController: NavController,
    viewModel: PostRecipeViewModel
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
    val titleFontSize by animateFloatAsState(if (pageState > LovePageState.Closed) 20f else 16f)
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

    val context = LocalContext.current
    var clicked by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }

    val recipe = data.recipe
    val ingredients = data.ingredients

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
                recipe.coverUrl?.let {
                    val downloadedImage = downloadPhoto(recipe.coverUrl, context)
                    Image(
                        painter = rememberImagePainter(downloadedImage),
                        contentDescription = "Recipe Featured Image",
                        Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .fillMaxWidth()
                            .aspectRatio(1.35f),
                        contentScale = ContentScale.Crop,
                        alignment = Alignment.Center
                    )
                }

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
                        recipe.title?.let {
                            androidx.compose.material3.Text(
                                it,
                                color = Color.Black,
                                fontSize = titleFontSize.sp,
                                fontWeight = FontWeight(titleFontWeight),
                                modifier = Modifier.width(240.dp)
                            )
                        }
                        Spacer(Modifier.height(titleSpacing))
//                        androidx.compose.material3.Text(
//                            recipe.tags.toString(),
//                            color = Gray,
//                            fontSize = subtitleFontSize.sp
//                        )
                    }
                    Spacer(Modifier.weight(1f))

                    Row {
                        OutlinedButton(
                            onClick = {
                                navController.navigate(ScreenRoute.EditPost.route + "/${recipe.id}")

//                                onPageClosing()
//                                currentcartLovePageState = LovePageState.Closed
                            },
                            modifier = Modifier.size(50.dp),
                            shape = CircleShape,
                            contentPadding = PaddingValues(0.dp),
//                        border = BorderStroke(5.dp, green100),
//                        colors = ButtonDefaults.buttonColors(green100)
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Edit,
                                contentDescription = "edit",
                                modifier = Modifier
                                    .padding(10.dp)
                                    .size(22.dp),
                                tint = Color.Black
                            )

                        }
                    }


                }
                androidx.compose.material3.Text(
                    "Description",
                    Modifier
                        .offset(0.dp, titleOffsetY)
                        .padding(14.dp, 24.dp, 14.dp, 14.dp),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                recipe.content?.let {
                    androidx.compose.material3.Text(
                        it,
                        Modifier
                            .offset(0.dp, titleOffsetY)
                            .padding(14.dp, 0.dp),
                        fontSize = 15.sp,
                        color = Gray

                    )
                }

                androidx.compose.material3.Text(
                    "Ingredients",
                    Modifier
                        .offset(0.dp, titleOffsetY)
                        .padding(14.dp, 24.dp, 14.dp, 14.dp),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

                if (ingredients.isNotEmpty()) {
                    for (i in ingredients) {
                        Row() {
                            i.name?.let {
                                androidx.compose.material3.Text(
                                    it,
                                    Modifier
                                        .offset(0.dp, titleOffsetY)
                                        .padding(14.dp, 0.dp),
                                    fontSize = 15.sp,
                                    color = Gray
                                )
                            }

                            i.num?.let {
                                androidx.compose.material3.Text(
                                    it.toString(),
                                    Modifier
                                        .offset(0.dp, titleOffsetY)
                                        .padding(14.dp, 0.dp),
                                    fontSize = 15.sp,
                                    color = Gray
                                )
                            }
                        }
                    }
                }
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
                    "go back",
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
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Surface(
                {
                    clicked = true
                    showDialog = true
                },
                Modifier
                    .align(Alignment.TopEnd)
                    .padding(14.dp, 32.dp),
                color = Color.White,
                shape = CircleShape,
                indication = rememberRipple()
            ) {
                Icon(
                    imageVector = Icons.Outlined.Delete,
                    contentDescription = "Delete",
                    Modifier
                        .padding(8.dp)
                        .size(26.dp), tint = Color.Black
                )
            }

            if (clicked and showDialog) {
                AlertDialog(
                    onDismissRequest = { showDialog = false },
                    title = { androidx.compose.material3.Text(text = "Delete") },
                    text = { androidx.compose.material3.Text(text = "Are you sure to delete this recipe?") },
                    buttons = {
                        Row() {
                            TextButton(
                                onClick = {
                                    recipe.id?.let { viewModel.deletdById(recipeId = it) }
//                                    onPageClosing()
                                    navController.navigate(ScreenRoute.HomeScreen.route)
                                }
                            ) {
                                androidx.compose.material3.Text(text = "Yes")
                            }
                            TextButton(
                                onClick = { clicked = false }
                            ) {
                                androidx.compose.material3.Text(text = "Cancel")
                            }
                        }

                    },
                    modifier = Modifier.background(BackgroundWhite)
                )
            }
        }
    }
}

private fun downloadPhoto(
    coverUrl: String,
    context: Context
): File {
    val photoKey = "$coverUrl.png"
    val filePath = "${context.filesDir}/${photoKey}"

    Log.i("downloadPhoto", "home photoKey is: $photoKey")

    if (!fileIsExists(filePath)) {
        val localFile = File(filePath)
        Amplify.Storage.downloadFile(
            photoKey,
            localFile,
            { },
            { Log.e("downloadPhoto", "Failed download", it) }
        )
    }

    return File(filePath)
}

private fun fileIsExists(filePath: String): Boolean {
    try {
        val f = File(filePath)
        if (!f.exists()) {
            return false
        }
    } catch (e: Exception) {
        return false
    }
    return true
}


enum class LovePageState {
    Closing, Closed, Opening, Open
}