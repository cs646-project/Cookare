package com.example.cookare.ui.profile


import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.*
import androidx.compose.ui.util.lerp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.cookare.model.User
import com.example.cookare.model.users
import com.example.cookare.ui.components.CookareDivider
import com.example.cookare.ui.components.CookareSurface
import com.example.cookare.ui.components.InputField
import com.example.cookare.ui.components.UserImage
import com.example.cookare.ui.theme.*
import com.example.cookare.ui.utils.ScreenRoute
import kotlin.math.max
import kotlin.math.min



private val BottomBarHeight = 56.dp
private val TitleHeight = 128.dp
private val GradientScroll = 180.dp
private val ImageOverlap = 115.dp
private val MinTitleOffset = 56.dp
private val MinImageOffset = 12.dp
private val MaxTitleOffset = ImageOverlap + MinTitleOffset + GradientScroll
private val ExpandedImageSize = 300.dp
private val CollapsedImageSize = 150.dp
private val HzPadding = Modifier.padding(horizontal = 24.dp)


@Composable
fun ProfileScreen(navController: NavController) {
    Box(Modifier.fillMaxSize()) {
        Profile(navController,user= users)


    }
}
@Composable
fun Profile(navController:NavController,user:User) {

    Box(Modifier.fillMaxSize()) {
        val scroll = rememberScrollState(0)

        Header(navController)
        Body(scroll)
        Title() { scroll.value }
        Image(user.avatar_url) { scroll.value }
    }
}
@Composable
private fun Header(navController: NavController) {

    AsyncImage(
        model = "https://img.lookvin.com/editor/201809/04/13434993.jpg",
        contentDescription = "",
        modifier = Modifier
            .fillMaxWidth()
    )
    OutlinedButton(
        onClick = {navController.navigate(ScreenRoute.HomeScreen.route)},
        modifier = Modifier
            .size(60.dp)
            .padding(12.dp),
        shape = CircleShape,
        border = BorderStroke(1.5.dp, Color.White),
        contentPadding = PaddingValues(0.dp),
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "go back",
            modifier = Modifier
                .padding(8.dp)
                .size(30.dp),
            tint = Color.White
        )
    }

}

@Composable
private fun Body(
    scroll: ScrollState
) {
    Column {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .height(MinTitleOffset)
        )
        Column(
            modifier = Modifier.verticalScroll(scroll)

        ) {

            Spacer(Modifier.height(GradientScroll))
            CookareSurface(Modifier.fillMaxWidth()) {
                Column() {
                    Spacer(Modifier.height(ImageOverlap))
                    Spacer(Modifier.height(TitleHeight))
                    Spacer(Modifier.height(16.dp))
                    Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                        InputField(cata = "User Name", input_item = users.username )
                        InputField(cata = "Email", input_item = users.email )
                        InputField(cata = "Password", input_item = users.password)
                        InputField(cata = "Signature", input_item = users.tags )


                        Spacer(Modifier.height(4.dp))
                        Row(modifier = Modifier.fillMaxWidth()) {
                            Button(
                                onClick = {

                                },
                                modifier = Modifier

                                    .padding(vertical = 30.dp, horizontal = 40.dp)
                                    .height(50.dp)
                                    .clip(CircleShape)
                            ) {

                                androidx.compose.material3.Text(text = "Update")

                            }
                            Button(
                                onClick = {

                                },
                                modifier = Modifier

                                    .padding(vertical = 30.dp, horizontal = 30.dp)
                                    .height(50.dp)
                                    .clip(CircleShape)
                            ) {

                                androidx.compose.material3.Text(text = "Log Out")

                            }
                        }
                    }

                }
            }
        }
    }
}

@Composable
private fun Title(scrollProvider: () -> Int) {
    val maxOffset = with(LocalDensity.current) { MaxTitleOffset.toPx() }
    val minOffset = with(LocalDensity.current) { MinTitleOffset.toPx() }

    /*val names = listOf("Posts", "Followers", "Following")
    var selected by remember { mutableStateOf(0) }*/


    Column(
        verticalArrangement = Arrangement.Bottom,
        modifier = Modifier
            .heightIn(min = TitleHeight)
            .statusBarsPadding()

            .offset {
                val scroll = scrollProvider()
                val offset = (maxOffset - scroll).coerceAtLeast(minOffset)
                IntOffset(x = 0, y = offset.toInt())
            }
            .background(color = Neutral0)
    ) {
        Spacer(Modifier.height(16.dp))
        Text(
            text = "Profile",
            style = MaterialTheme.typography.h2,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 6.dp, start = 20.dp)
        )
        CookareDivider()


    }
}

@Composable
private fun Image(
    imageUrl: String,
    scrollProvider: () -> Int
) {
    val collapseRange = with(LocalDensity.current) { (MaxTitleOffset - MinTitleOffset).toPx() }
    val collapseFractionProvider = {
        (scrollProvider() / collapseRange).coerceIn(0f, 1f)
    }

    CollapsingImageLayout(
        collapseFractionProvider = collapseFractionProvider,
        modifier = HzPadding.then(Modifier.statusBarsPadding())
    ) {
        UserImage(
            imageUrl = imageUrl,
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
private fun CollapsingImageLayout(
    collapseFractionProvider: () -> Float,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Layout(
        modifier = modifier,
        content = content
    ) { measurables, constraints ->
        check(measurables.size == 1)

        val collapseFraction = collapseFractionProvider()

        val imageMaxSize = min(ExpandedImageSize.roundToPx(), constraints.maxWidth)
        val imageMinSize = max(CollapsedImageSize.roundToPx(), constraints.minWidth)
        val imageWidth = lerp(imageMaxSize, imageMinSize, collapseFraction)
        val imagePlaceable = measurables[0].measure(Constraints.fixed(imageWidth, imageWidth))

        val imageY = lerp(MinTitleOffset, MinImageOffset, collapseFraction).roundToPx()
        val imageX = lerp(
            (constraints.maxWidth - imageWidth) / 2, // centered when expanded
            constraints.maxWidth - imageWidth, // right aligned when collapsed
            collapseFraction
        )
        layout(
            width = constraints.maxWidth,
            height = imageY + imageWidth
        ) {
            imagePlaceable.placeRelative(imageX, imageY)
        }
    }
}
/*@Composable
fun RecipeArea(recipe:Recipe) {

    Column(Modifier.padding(24.dp, 24.dp, 24.dp, 0.dp)) {
        Surface(
            Modifier
                .fillMaxWidth()
                .padding(top = 12.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color.White)
                .padding(8.dp),
        ) {
            Row(Modifier.height(IntrinsicSize.Max)) {
                Image(
                    painterResource(recipe.imageId),
                    "图像",
                    Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .size(80.dp),
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.Center
                )
                Column(
                    Modifier
                        .padding(12.dp, 0.dp)
                        .fillMaxHeight(), verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    androidx.compose.material3.Text(recipe.time, fontSize = 14.sp, color = Color(0xffb4b4b4))
                    androidx.compose.material3.Text(recipe.name, fontSize = 16.sp)
                    androidx.compose.material3.Text(recipe.city, fontSize = 14.sp, color = Color(0xffb4b4b4))
                }
            }
        }
    }
}
*/


/*@Composable
fun Profile(navController:NavController,user:User) {
    /*val user = User(
        id = 1L,
        username = "Karina",
        tags = "I eat an apple a day",
        avatar_url = "https://source.unsplash.com/pGM4sjt_BdQ",
        password = "123"

    )*/

    Box(Modifier.fillMaxSize()) {
        val scroll = rememberScrollState(0)
        Header()
        Body(scroll,navController)
        Title(user) { scroll.value }
        Image(user.avatar_url) { scroll.value }
    }
}*/
/*data class Recipe(
    val name: String,
    val city: String,
    val time: String,
    @DrawableRes val imageId: Int
)
/*private val recipe1 = Recipe("Tomato Fish", "waterloo", "5 minutes ago", R.drawable.ic_pic1)
private val recipe2 = Recipe("Fish Soup ", "waterloo", "yesterday", R.drawable.ic_pic2)
private val recipe3 = Recipe("Green Egg", "waterloo", "yesterday", R.drawable.ic_pic3)
*/

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {



}*/