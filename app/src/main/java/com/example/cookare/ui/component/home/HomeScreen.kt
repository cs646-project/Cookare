package com.example.cookare.ui.component.home

import android.os.Build
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.cookare.ui.theme.*


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cookare.R

import com.example.cookare.ui.theme.BackgroundWhite
import com.example.cookare.ui.theme.Gray
import com.example.cookare.ui.theme.Orange
import com.example.cookare.ui.theme.LightPink

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalAnimationApi::class,
    ExperimentalMaterialApi::class)

var fullSize by mutableStateOf(IntSize(0, 0))
@Composable
fun HomeScreen() {
    Column(Modifier.onSizeChanged { fullSize = it }) {
        Column(
            Modifier.fillMaxWidth().weight(1f).background(BackgroundWhite)
                .verticalScroll(rememberScrollState())
        ) {

            TopBar()
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
fun TopBar() {
    Row(Modifier.fillMaxWidth().padding(28.dp, 28.dp, 28.dp, 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(painterResource(R.drawable.avatar_lia), "头像",
            Modifier.clip(CircleShape).size(64.dp))
        Column(Modifier.padding(start = 14.dp).weight(1f)) {
            Text("welcome back！", fontSize = 14.sp, color = Gray)
            Text("Karina", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }
        Surface(Modifier.clip(CircleShape), color = LightPink) {
            Image(painterResource(R.drawable.ic_notification_new), "通知",
                Modifier.padding(10.dp).size(32.dp))
        }
    }
}



@Composable
fun SearchBar() {
    Row(Modifier.padding(24.dp, 2.dp, 24.dp, 6.dp).fillMaxWidth().height(56.dp)
        .clip(RoundedCornerShape(28.dp)).background(Color.White),
        verticalAlignment = Alignment.CenterVertically
    ) {
        var searchText by remember { mutableStateOf("") }
        BasicTextField(searchText, { searchText = it },
            Modifier.padding(start = 24.dp).weight(1f),
            textStyle = TextStyle(fontSize = 15.sp)
        ) {
            if (searchText.isEmpty()) {
                Text("Search", color = Color(0xffb4b4b4), fontSize = 15.sp)
            }
            it()
        }
        Box(Modifier.padding(6.dp).fillMaxHeight().aspectRatio(1f)
            .clip(CircleShape).background(Color(0xfffa9e51))
        ) {
            Icon(painterResource(R.drawable.ic_search), "搜索",
                Modifier.size(24.dp).align(Alignment.Center), tint = Color.White
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
            Column(Modifier.padding(12.dp, 4.dp).width(IntrinsicSize.Max)) {
                Text(name, fontSize = 15.sp,
                    color = if (index == selected) Color(0xfffa9e51) else Color(0xffb4b4b4)
                )
                Box(Modifier.fillMaxWidth().padding(top = 4.dp).height(2.dp).clip(RoundedCornerShape(1.dp))
                    .background(if (index == selected) Color(0xfffa9e51) else Color.Transparent
                    )
                )
            }
        }
    }
}

@Composable
fun PlaceArea() {
    val place = Place("university of waterloo", "waterloo", "5 minutes age", R.drawable.ic_pic1)
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
