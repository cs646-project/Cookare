package com.example.cookare.ui.component.home

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.cookare.ui.theme.*

import androidx.annotation.DrawableRes
import androidx.compose.foundation.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
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
import com.example.cookare.ui.theme.LightPink
import com.example.cookare.ui.utils.ScreenRoute

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalAnimationApi::class,
    ExperimentalMaterialApi::class)

var fullSize by mutableStateOf(IntSize(0, 0))

@Composable
fun HomeScreen(navController:NavController) {
    Column(Modifier.onSizeChanged { fullSize = it }) {
        Column(
            Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(BackgroundWhite)
                .verticalScroll(rememberScrollState())
        ) {
            TopBar(navController)
            SearchBar()
            NamesBar()
            PlaceArea()
            PlaceArea()
            PlaceArea()
        }
    }

    // TODO
}

@Composable
fun TopBar(navController:NavController) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(28.dp, 28.dp, 28.dp, 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(painterResource(R.drawable.avatar_lia), "头像",
            Modifier
                .clip(CircleShape)
                .size(64.dp))
        Column(
            Modifier
                .padding(start = 14.dp)
                .weight(1f)) {
            Text("welcome back！", fontSize = 14.sp, color = Gray)
            Text("Karina", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }
        OutlinedButton(
            onClick = { navController.navigate(ScreenRoute.NotificationScreen.route) },
            modifier= Modifier.size(50.dp),
            shape = CircleShape,
            border= BorderStroke(5.dp, LightPink),
            contentPadding = PaddingValues(0.dp),
            colors = ButtonDefaults.buttonColors(containerColor = LightPink)
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
            border= BorderStroke(5.dp, LightRed),
            contentPadding = PaddingValues(0.dp),
            colors = ButtonDefaults.buttonColors(containerColor = LightRed)
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
                Text("Search", color = Color(0xffb4b4b4), fontSize = 15.sp)
            }
            it()
        }
        Box(
            Modifier
                .padding(6.dp)
                .fillMaxHeight()
                .aspectRatio(1f)
                .clip(CircleShape)
                .background(Color(0xfffa9e51))
        ) {
            Icon(painterResource(R.drawable.ic_search), "搜索",
                Modifier
                    .size(24.dp)
                    .align(Alignment.Center), tint = Color.White
            )
        }
    }
}

@Composable
fun NamesBar() {
    val names = listOf("recommend", "following","latest","hot")
    var selected by remember { mutableStateOf(0) }
    LazyRow(Modifier.padding(0.dp, 8.dp), contentPadding = PaddingValues(12.dp, 0.dp)) {
        itemsIndexed(names) { index, name ->
            Column(
                Modifier
                    .padding(12.dp, 4.dp)
                    .width(IntrinsicSize.Max)) {
                Text(name, fontSize = 15.sp,
                    color = if (index == selected) Color(0xfffa9e51) else Color(0xffb4b4b4)
                )
                Box(
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp)
                        .height(2.dp)
                        .clip(RoundedCornerShape(1.dp))
                        .background(
                            if (index == selected) Color(0xfffa9e51) else Color.Transparent
                        )
                )
            }
        }
    }
}

@Composable
fun PlaceArea() {
    val place = Place("university of waterloo", "waterloo", "5 minutes ago", R.drawable.ic_pic1)
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
                    painterResource(place.imageId),
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
                    Text(place.time, fontSize = 14.sp, color = Color(0xffb4b4b4))
                    Text(place.name, fontSize = 16.sp)
                    Text(place.city, fontSize = 14.sp, color = Color(0xffb4b4b4))
                }
            }
        }
    }
}


data class Place(
    val name: String,
    val city: String,
    val time: String,
    @DrawableRes val imageId: Int
)
