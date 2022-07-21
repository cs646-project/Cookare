package com.example.cookare.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.cookare.R
import com.example.cookare.model.Ingredient
import com.example.cookare.model.Recipe
import com.example.cookare.ui.MainActivity
import com.example.cookare.ui.auth
import com.example.cookare.ui.home.isNumber
import com.example.cookare.ui.theme.CookareTheme
import com.example.cookare.ui.theme.green000
import com.example.cookare.ui.userId
import com.example.cookare.viewModels.PostRecipeViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class EditPostActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val searchId = Integer.parseInt(intent.getStringExtra("id"))
        Log.d("searchId", "recipeId $searchId")

        setContent {
            CookareTheme {
                EditRecipeScreen(searchId, hiltViewModel())
            }
        }
    }
}

@Composable
fun EditRecipeScreen(
    recipeId: Int,
    viewModel: PostRecipeViewModel
) {
    viewModel.searchById(listOf(recipeId))
    var data = viewModel.resRecipeByIdList.value
    val recipes = data.map { it.recipe }
    val ingredients = data.map { it.ingredients }
    var recipe = if (recipes.isNotEmpty()) recipes[0] else null

    var title by mutableStateOf(if (recipe != null) recipe.title else "")
    var content by  mutableStateOf(if (recipe != null) recipe.content else "")
    var tags by  mutableStateOf(recipe?.tags?.toString() ?: "")
    var updateUser by  mutableStateOf(if (recipe != null) recipe.updateUser else "")
    var coverUrl by mutableStateOf(if (recipe != null) recipe.coverUrl else "")

    var num by mutableStateOf(if(ingredients.isNotEmpty()) ingredients[0].size else 0)
    var count by remember { mutableStateOf(0)}

    var ingredientNameMap: MutableMap<String, MutableState<String?>> = remember { mutableMapOf() }
    var ingredientNumMap: MutableMap<String, MutableState<String>> = remember { mutableMapOf() }
    for (index in 1..(num+count)) {
        ingredientNameMap["ingredientName$index"] = remember { mutableStateOf( if (index <= num && ingredients[0][index-1].name != null) ingredients[0][index-1].name else "") }
        ingredientNumMap["ingredientNum$index"] = remember { mutableStateOf(if(index <= num && ingredients[0][index-1].num.toString() != "null") ingredients[0][index-1].num.toString() else "") }
    }

    val context = LocalContext.current

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
                            var intent = Intent(context, MainActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP;
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK;
                            intent.putExtra("token", auth)
                            intent.putExtra("id", userId)
                            context.startActivity(intent)
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
                                append("Edit Your Recipe")
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

            title?.let { it1 ->
                OutlinedTextField(
                    value = it1,
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    label = { Text(text = "Title") },
                    placeholder = { Text(text = "") },
                    onValueChange = {
                        title = it
                    }
                )
            }

            content?.let { it1 ->
                OutlinedTextField(
                    value = it1,
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    label = { Text(text = "Content") },
                    onValueChange = {
                        content = it
                    }
                )
            }

            OutlinedTextField(
                value = tags,
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                label = { Text(text = "Tags") },
                onValueChange = {
                    tags = it
                }
            )

            coverUrl?.let { it1 ->
                OutlinedTextField(
                    value = it1,
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    label = { Text(text = "Image url") },
                    onValueChange = {
                        coverUrl = it
                    }
                )
            }

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
                        count += 1
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


            for (index in 1..(num+count)) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    ingredientNameMap["ingredientName$index"]!!.value?.let { it1 ->
                        OutlinedTextField(
                            value = it1,
                            modifier = Modifier
                                .padding(8.dp)
                                .weight(1f),
                            label = { Text(text = "Name$index") },
                            placeholder = { Text(text = "") },
                            onValueChange = {
                                ingredientNameMap["ingredientName$index"]!!.value = it
                            }
                        )
                    }

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
                            recipeId,
                            title,
                            content,
                            Integer.parseInt(tags),
                            userId,
                            coverUrl,
                            (1..num+count).map{
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

                    var intent = Intent(context, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP;
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK;
                    intent.putExtra("token", auth)
                    intent.putExtra("id", userId)
                    context.startActivity(intent)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
                    .height(50.dp)
                    .clip(CircleShape)
            ) {
                Text(text = "Edit")
            }
        }
    }
}
