package com.example.cookare.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.cookare.R
import com.example.cookare.model.User
import com.example.cookare.ui.utils.ScreenRoute
import com.example.cookare.ui.theme.*

@Composable
fun TopBar(users:User,navController:NavController) {
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
            AsyncImage(
                model = users.avatar_url,
                contentDescription = "avatar",
                modifier = Modifier
                    .clip(CircleShape)
                    .fillMaxWidth()
                    .background(color = Gray100)
            )

        }
        Column(
            Modifier
                .padding(start = 14.dp)
                .weight(1f)) {
            Text("Welcome back！", fontSize = 18.sp, color = Gray100)
            Text(users.username, fontSize = 18.sp, color = Color.Black, fontWeight = FontWeight.Bold)
            Text(users.tags, fontSize = 14.sp, color = Gray)
        }
       /* OutlinedButton(
            onClick = {},
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
        */

        OutlinedButton(
            onClick = { navController.navigate(ScreenRoute.ProfileScreen.route) },
            modifier= Modifier.size(50.dp),
            shape = CircleShape,
            border= BorderStroke(5.dp, green100),
            contentPadding = PaddingValues(0.dp),
            colors = ButtonDefaults.buttonColors(green100)
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_settings),
                contentDescription = "setting",
                modifier = Modifier
                    .padding(10.dp)
                    .size(22.dp),
                tint = Color.Black
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {

}


