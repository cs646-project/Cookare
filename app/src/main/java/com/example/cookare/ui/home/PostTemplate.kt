package com.example.cookare.ui.home

import android.util.Log
import android.widget.Toast
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.cookare.R
import com.example.cookare.model.Ingredient
import com.example.cookare.model.Recipe
import com.example.cookare.ui.theme.CookareTheme
import com.example.cookare.ui.theme.green000
import com.example.cookare.ui.userId
import com.example.cookare.ui.utils.ScreenRoute
import com.example.cookare.viewModels.PostRecipeViewModel

@Composable
fun PostTemplate(navController: NavController, viewModel: PostRecipeViewModel) {
    var num by remember { mutableStateOf(2) }

    var ingredientNameMap: MutableMap<String, MutableState<String>> = remember { mutableMapOf() }
    var ingredientNumMap: MutableMap<String, MutableState<String>> = remember { mutableMapOf() }

    for (index in 1..num) {
        ingredientNameMap["ingredientName$index"] = remember { mutableStateOf("") }
        ingredientNumMap["ingredientNum$index"] = remember { mutableStateOf("") }
    }

    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    var tags by remember { mutableStateOf("") }
    var updateUser by remember { mutableStateOf("") }
    var coverUrl by remember { mutableStateOf("") }

    LazyColumn(state = rememberLazyListState()) {
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
                        onClick = {
                            navController.navigateUp()
                                  },
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

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    "Ingredients",
                    Modifier
                        .padding(14.dp, 24.dp, 14.dp, 14.dp),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

                OutlinedButton(
                    onClick = {
                        num += 1
                        Log.d("Debug" , "ShowResult: Click!!!! $num")
                      },
                    modifier = Modifier
                        .size(30.dp),
                    shape = CircleShape,
                    border = BorderStroke(1.5.dp, green000),
                    colors = ButtonDefaults.buttonColors(green000),
                    contentPadding = PaddingValues(0.dp),
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_add),
                        contentDescription = "add ingredients",
                        modifier = Modifier
                            .padding(6.dp)
                            .size(60.dp),
                        tint = Color.White
                    )
                }
            }


            for (index in 1..num) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    OutlinedTextField(
                        value = ingredientNameMap["ingredientName$index"]!!.value,
                        modifier = Modifier
                            .padding(8.dp)
                            .weight(1f),
                        label = { Text(text = "Name$index") },
                        placeholder = { Text(text = "") },
                        onValueChange = {
                            ingredientNameMap["ingredientName$index"]!!.value = it
                        }
                    )

                    OutlinedTextField(
                        value = ingredientNumMap["ingredientNum$index"]!!.value,
                        modifier = Modifier
                            .padding(8.dp)
                            .weight(1f),
                        label = { Text(text = "Number$index") },
                        placeholder = { Text(text = "") },
                        onValueChange = {
                            ingredientNumMap["ingredientNum$index"]!!.value = it
                        }
                    )
                }

            }
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
                            userId,
                            coverUrl,
                            (1..num).map{
                                Ingredient(
                                    if(ingredientNameMap["ingredientName$it"]?.value != "") ingredientNameMap["ingredientName$it"]!!.value else "",
                                    if(ingredientNumMap["ingredientNum$it"]?.value != "" && isNumber(ingredientNumMap["ingredientNum$it"]?.value)) Integer.parseInt(ingredientNumMap["ingredientNum$it"]!!.value) else 0
                                )
                            }.filter {
                                it.name != ""
                            }.filter {
                                it.num != 0
                            }
                        )
                    )
                    navController.navigateUp()
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


fun isNumber(s: String?): Boolean {
    return if (s.isNullOrEmpty()) false else s.all { Character.isDigit(it) }
}
