package com.example.cookare.ui.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.InternalTextApi
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.cookare.ui.theme.CookareTheme
import com.example.cookare.ui.utils.ScreenRoute

@Composable
fun PostTemplate(navController:NavController) {
    LazyColumn(state = rememberLazyListState()) {
        item { TextInputs(navController) }

        item {
            var loading by remember { mutableStateOf(false) }
            Button(
                onClick = {
                    loading = true
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
                    .height(50.dp)
                    .clip(CircleShape)
            ) {
                if (loading) {
                    HorizontalDottedProgressBar()
                } else {
                    Text(text = "Add")
                }
            }
        }
    }
}

@Composable
fun TextInputs(navController: NavController){
    var title by remember { mutableStateOf(TextFieldValue("")) }
    var description by remember { mutableStateOf(TextFieldValue("")) }
    var tips by remember { mutableStateOf(TextFieldValue("")) }
    var time by remember { mutableStateOf(TextFieldValue("")) }
    var level by remember { mutableStateOf(TextFieldValue("")) }
    var tag by remember { mutableStateOf(TextFieldValue("")) }
    var step_1 by remember { mutableStateOf(TextFieldValue("")) }
    var step_2 by remember { mutableStateOf(TextFieldValue("")) }
    var step_3 by remember { mutableStateOf(TextFieldValue("")) }

    Column{
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .background(color = CookareTheme.colors.onPrimaryContainer),
//            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedButton(
                onClick = {navController.navigate(ScreenRoute.HomeScreen.route)},
                modifier = Modifier.size(60.dp).padding(12.dp),
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

            Text(
                buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.White,
                        )
                    ) {
                        append("Add Your Post")
                    }
                }
            )
        }
    }


    OutlinedTextField(
        value = title,
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        label = { Text(text = "Title") },
        placeholder = { Text(text = "") },
        onValueChange = {
            title = it
        }
    )

    OutlinedTextField(
        value = description,
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        label = { Text(text = "Description") },
        placeholder = { Text(text = "") },
        onValueChange = {
            description = it
        }
    )

    OutlinedTextField(
        value = tips,
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        label = { Text(text = "Tips") },
        placeholder = { Text(text = "") },
        onValueChange = {
            tips = it
        }
    )

    OutlinedTextField(
        value = time,
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        label = { Text(text = "Time") },
        placeholder = { Text(text = "") },
        onValueChange = {
            time = it
        }
    )

    OutlinedTextField(
        value = level,
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        label = { Text(text = "Level") },
        placeholder = { Text(text = "") },
        onValueChange = {
            level = it
        }
    )

    OutlinedTextField(
        value = tag,
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        label = { Text(text = "Tag") },
        placeholder = { Text(text = "") },
        onValueChange = {
            tag = it
        }
    )

    Text(text = "Procedure", modifier = Modifier.padding(8.dp), fontSize = 20.sp, style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.ExtraBold),)

    OutlinedTextField(
        value = step_1,
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        label = { Text(text = "Step 1") },
        placeholder = { Text(text = "") },
        onValueChange = {
            step_1 = it
        }
    )

    OutlinedTextField(
        value = step_2,
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        label = { Text(text = "Step 2") },
        placeholder = { Text(text = "") },
        onValueChange = {
            step_2 = it
        }
    )

    OutlinedTextField(
        value = step_3,
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        label = { Text(text = "Step 3") },
        placeholder = { Text(text = "") },
        onValueChange = {
            step_3 = it
        }
    )
}
