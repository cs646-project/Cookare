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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.cookare.model.Recipe
import com.example.cookare.ui.theme.CookareTheme
import com.example.cookare.ui.utils.ScreenRoute
import com.example.cookare.viewModels.PostRecipeViewModel

@Composable
fun PostTemplate(navController: NavController, viewModel: PostRecipeViewModel) {
    LazyColumn(state = rememberLazyListState()) {
        var title by mutableStateOf("")
        var content by mutableStateOf("")
        var tags by mutableStateOf("")
        var updateUser by mutableStateOf("")
        var coverUrl by mutableStateOf("")

        item {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .background(color = CookareTheme.colors.onPrimaryContainer),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedButton(
                        onClick = {navController.navigate(ScreenRoute.HomeScreen.route) },
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

                    Text(
                        buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = Color.White,
                                )
                            ) {
                                append("Add Your Recipe")
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
                value = content,
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                label = { Text(text = "Content") },
                placeholder = { Text(text = "") },
                onValueChange = {
                    content = it
                }
            )

            OutlinedTextField(
                value = tags,
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                label = { Text(text = "Tags") },
                placeholder = { Text(text = "") },
                onValueChange = {
                    tags = it
                }
            )

            OutlinedTextField(
                value = coverUrl,
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                label = { Text(text = "Image url") },
                placeholder = { Text(text = "") },
                onValueChange = {
                    coverUrl = it
                }
            )
        }

        item {
            Button(
                onClick = {
                    viewModel.postRecipe(
                        Recipe(
                            null,
                            title,
                            content,
                            Integer.parseInt(tags),
                            1,
                            coverUrl,
                            null
                        )
                    )

                    navController.navigate(ScreenRoute.HomeScreen.route)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
                    .height(50.dp)
                    .clip(CircleShape)
            ) {
                Text(text = "Add")
            }
        }
    }
}
