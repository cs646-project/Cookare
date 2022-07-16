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
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.cookare.model.Ingredient
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
        var ingredientName by mutableStateOf("")
        var ingredientNum by mutableStateOf("")
        var ingredientName1 by mutableStateOf("")
        var ingredientNum1 by mutableStateOf("")

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
                        onClick = { navController.navigate(ScreenRoute.HomeScreen.route) },
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

            Text(
                "Recipe detail ",
                Modifier
                    .padding(14.dp, 24.dp, 14.dp, 14.dp),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )

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

            Text(
                "Ingredients",
                Modifier
                    .padding(14.dp, 24.dp, 14.dp, 14.dp),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )

            OutlinedTextField(
                value = ingredientName,
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                label = { Text(text = "Ingredient name -- 1") },
                placeholder = { Text(text = "") },
                onValueChange = {
                    ingredientName = it
                }
            )

            OutlinedTextField(
                value = ingredientNum,
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                label = { Text(text = "Ingredient number -- 1") },
                placeholder = { Text(text = "") },
                onValueChange = {
                    ingredientNum = it
                }
            )

            OutlinedTextField(
                value = ingredientName1,
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                label = { Text(text = "Ingredient name -- 2") },
                placeholder = { Text(text = "") },
                onValueChange = {
                    ingredientName1 = it
                }
            )

            OutlinedTextField(
                value = ingredientNum1,
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                label = { Text(text = "Ingredient number -- 2") },
                placeholder = { Text(text = "") },
                onValueChange = {
                    ingredientNum1 = it
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
                            listOf(
                                Ingredient(
                                    if(ingredientName == "") null else ingredientName,
                                    if(ingredientNum == "") null else Integer.parseInt(ingredientNum),
                                    null
                                ),
                                Ingredient(
                                    if(ingredientName == "") null else ingredientName1,
                                    if(ingredientNum == "") null else Integer.parseInt(ingredientNum1),
                                    null
                                )
                            )
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
