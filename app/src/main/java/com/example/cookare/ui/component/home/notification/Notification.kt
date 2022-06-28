package com.example.cookare.ui.component.home.notification

import androidx.annotation.DrawableRes
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.material3.IconButtonDefaults.iconButtonColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cookare.HomeScreenNavigate
import com.example.cookare.R
import com.example.cookare.ui.component.home.HomeScreen
import com.example.cookare.ui.component.home.collection.CollectionAndLikeScreen
import com.example.cookare.ui.component.home.collection.TabPage
import com.example.cookare.ui.theme.CookareTheme
import com.example.cookare.ui.theme.Neutral0
import com.example.cookare.ui.theme.Neutral8
import com.example.cookare.ui.theme.green100
import com.example.cookare.ui.utils.ScreenRoute
import com.example.cookare.ui.utils.mirroringBackIcon

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationScreen(
    upPress: () -> Unit
) {
    val notification1 = Notification(
        "Karina",
        "liked",
        "photo",
        "2 minutes ago",
        R.drawable.avatar_lia,
        R.drawable.ic_pic1
    )

    val notification2 = Notification(
        "Steven",
        "liked",
        "photo",
        "5 minutes ago",
        R.drawable.ic_user2,
        R.drawable.ic_pic2
    )

    val notification3 = Notification(
        "Steven",
        "liked",
        "photo",
        "7 minutes ago",
        R.drawable.ic_user2,
        R.drawable.ic_pic3
    )

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .background(color = MaterialTheme.colorScheme.onPrimaryContainer),
//            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Up(upPress)

            Text(
                buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.White,
                        )
                    ) {
                        append(" Notification")
                    }
                }
            )
        }

        Column(Modifier.padding(
            start = 24.dp,
            top = 0.dp,
            bottom = 24.dp,
            end = 24.dp)
        ) {
            Surface(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp)
                    .clip(RoundedCornerShape(5.dp))
                    .background(Color.White)
                    .padding(8.dp),
            ) {
                NotificationItem(notification = notification1)
            }
            Surface(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp)
                    .clip(RoundedCornerShape(5.dp))
                    .background(Color.White)
                    .padding(8.dp),
            ) {
                NotificationItem(notification = notification2)
            }
            Surface(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp)
                    .clip(RoundedCornerShape(5.dp))
                    .background(Color.White)
                    .padding(8.dp),
            ) {
                NotificationItem(notification = notification3)
            }
        }
    }
}

@Composable
private fun Up(upPress: () -> Unit) {
    androidx.compose.material.IconButton(
        onClick = upPress,
        modifier = Modifier
            .statusBarsPadding()
            .padding(horizontal = 16.dp, vertical = 10.dp)
            .size(36.dp)
            .background(
                color = Neutral8.copy(alpha = 0.32f),
                shape = CircleShape
            )
    ) {
        androidx.compose.material.Icon(
            imageVector = mirroringBackIcon(),
            tint = Neutral0,
            contentDescription = stringResource(R.string.label_back)
        )
    }
}

@Composable
fun NotificationItem(notification: Notification) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painterResource(notification.guestAvatar), "avatar",
            Modifier
                .clip(CircleShape)
                .size(40.dp),
        )

        Text(
            buildAnnotatedString {
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append(notification.guestName)
                }
                append(" ")
                append(notification.activity)
                append(" your ")
                append(notification.postType)
                append(".")
            },
        )
        Image(
            painterResource(notification.postId), "post",
            Modifier
                .clip(RoundedCornerShape(5.dp))
                .size(55.dp),
            contentScale = ContentScale.Crop
        )
    }
}
//
//@Composable
//fun NotificationNavigate() {
//    val navController = rememberNavController()
//    NavHost(navController = navController, startDestination = ScreenRoute.NotificationScreen.route){
//        composable(route = ScreenRoute.NotificationScreen.route){
//            NotificationScreen(navController = navController)
//        }
//        composable(route = ScreenRoute.HomeScreen.route){
//            HomeScreenNavigate()
//        }
//    }
//}

data class Notification(
    val guestName: String,
    val activity: String,
    val postType: String,
    val time: String,
    @DrawableRes val guestAvatar: Int,
    @DrawableRes val postId: Int
)

/*
@Preview
@Composable
private fun PreviewNotificationScreen() {
    CookareTheme {
        NotificationScreen()
    }
}

 */