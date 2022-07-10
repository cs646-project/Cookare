package com.example.cookare.ui.profile


import androidx.annotation.DrawableRes
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import androidx.compose.ui.util.lerp
import coil.compose.AsyncImage
import com.example.cookare.R
import com.example.cookare.model.User
import com.example.cookare.ui.components.CookareDivider
import com.example.cookare.ui.components.CookareSurface
import com.example.cookare.ui.components.UserImage
import com.example.cookare.ui.theme.*
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

private val recipe1 = Recipe("Tomato Fish", "waterloo", "5 minutes ago", R.drawable.ic_pic1)
private val recipe2 = Recipe("Fish Soup ", "waterloo", "yesterday", R.drawable.ic_pic2)
private val recipe3 = Recipe("Green Egg", "waterloo", "yesterday", R.drawable.ic_pic3)

@Composable
fun ProfileScreen() {
    Box(Modifier.fillMaxSize()) {
        Profile()
    }
}

@Composable
fun Profile() {
    val user = User(
        id = 1L,
        name = "Karina",
        tagline = "I eat an apple a day",
        imageUrl = "https://source.unsplash.com/pGM4sjt_BdQ",
        following = 299,
        follower = 299
    )

    Box(Modifier.fillMaxSize()) {
        val scroll = rememberScrollState(0)
        Header()
        Body(scroll)
        Title(user) { scroll.value }
        Image(user.imageUrl) { scroll.value }
    }
}

@Composable
private fun Header() {

    AsyncImage(
        model = "https://img.lookvin.com/editor/201809/04/13434993.jpg",
        contentDescription = "",
        modifier = Modifier
            .fillMaxWidth()
    )



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
                Column {
                    Spacer(Modifier.height(ImageOverlap))
                    Spacer(Modifier.height(TitleHeight))

                    Spacer(Modifier.height(16.dp))

                    /*
                    Text(
                        text = stringResource(R.string.detail_header),
                        style = MaterialTheme.typography.overline,
                        color = Neutral6,
                        modifier = HzPadding
                    )

                     */
                    // Spacer(Modifier.height(25.dp))

                    /*
                    var seeMore by remember { mutableStateOf(true) }
                    Text(
                        text = stringResource(R.string.detail_placeholder),
                        style = MaterialTheme.typography.body1,
                        color = Neutral6,
                        maxLines = if (seeMore) 5 else Int.MAX_VALUE,
                        overflow = TextOverflow.Ellipsis,
                        modifier = HzPadding
                    )
                    val textButton = if (seeMore) {
                        stringResource(id = R.string.see_more)
                    } else {
                        stringResource(id = R.string.see_less)
                    }
                    Text(
                        text = textButton,
                        style = MaterialTheme.typography.button,
                        textAlign = TextAlign.Center,
                        color = Ocean11,
                        modifier = Modifier
                            .heightIn(20.dp)
                            .fillMaxWidth()
                            .padding(top = 15.dp)
                            .clickable {
                                seeMore = !seeMore
                            }
                    )

                     */

                    // Spacer(Modifier.height(16.dp))
                    // CookareDivider()
                    RecipeArea(recipe = recipe1)
                    RecipeArea(recipe = recipe2)
                    RecipeArea(recipe = recipe3)
                    Spacer(
                        modifier = Modifier
                            .padding(bottom = BottomBarHeight)
                            .navigationBarsPadding()
                            .height(8.dp)
                    )
                }
            }
        }
    }
}
@Composable
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


@Composable
private fun Title(user: User, scrollProvider: () -> Int) {
    val maxOffset = with(LocalDensity.current) { MaxTitleOffset.toPx() }
    val minOffset = with(LocalDensity.current) { MinTitleOffset.toPx() }

    val names = listOf("Posts", "Followers", "Following")
    var selected by remember { mutableStateOf(0) }


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
            text = user.name,
            style = MaterialTheme.typography.h4,
            color = Neutral7,
            modifier = HzPadding
        )
        Text(
            text = user.tagline,
            style = MaterialTheme.typography.subtitle2,
            fontSize = 20.sp,
            color = Neutral6,
            modifier = HzPadding
        )
        Spacer(Modifier.height(4.dp))
        LazyRow(Modifier.padding(0.dp, 8.dp), contentPadding = PaddingValues(12.dp, 0.dp)) {
            itemsIndexed(names) { index, name ->
                Column(
                    Modifier
                        .padding(12.dp, 4.dp)
                        .width(IntrinsicSize.Max)) {
                    Text(name, fontSize = 15.sp,
                        color = if (index == selected) green000 else Gray
                    )
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .padding(top = 4.dp)
                            .height(2.dp)
                            .clip(RoundedCornerShape(1.dp))
                            .background(
                                if (index == selected) green000 else Color.Transparent
                            )
                    )
                }
            }
        }

        Spacer(Modifier.height(8.dp))
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

data class Recipe(
    val name: String,
    val city: String,
    val time: String,
    @DrawableRes val imageId: Int
)

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {



}