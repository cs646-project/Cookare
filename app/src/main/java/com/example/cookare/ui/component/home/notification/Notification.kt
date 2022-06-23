package com.example.cookare.ui.component.home.notification

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cookare.R
import com.example.cookare.ui.theme.CookareTheme

@Composable
fun NotificationScreen(){
    val notification = Notification("User1",
        "liked",
        "photo",
        "2 minutes ago",
        R.drawable.avatar_lia,
        R.drawable.ic_pic1
    )
    Column(
        Modifier.padding(0.dp, 15.dp, 15.dp, 0.dp)
    ) {
        Text(text = "Notification")
        Column(Modifier.padding(24.dp, 24.dp, 24.dp, 0.dp)){
            Surface(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp)
                    .clip(RoundedCornerShape(5.dp))
                    .background(Color.White)
                    .padding(8.dp),
            ){
                NotificationItem(notification = notification)
            }
            Surface(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp)
                    .clip(RoundedCornerShape(5.dp))
                    .background(Color.White)
                    .padding(8.dp),
            ){
                NotificationItem(notification = notification)
            }
            Surface(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp)
                    .clip(RoundedCornerShape(5.dp))
                    .background(Color.White)
                    .padding(8.dp),
            ){
                NotificationItem(notification = notification)
            }
        }
    }
}

@Composable
fun NotificationItem(notification: Notification){
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement  =  Arrangement.SpaceBetween,
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

data class Notification(
    val guestName: String,
    val activity: String,
    val postType: String,
    val time: String,
    @DrawableRes val guestAvatar: Int,
    @DrawableRes val postId: Int
)

@Preview
@Composable
private fun PreviewNotificationScreen(){
    CookareTheme {
        NotificationScreen()
    }
}