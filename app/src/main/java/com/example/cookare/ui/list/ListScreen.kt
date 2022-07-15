package com.example.cookare.ui.list

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextButton
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.cookare.mapper.EntityMapper
import com.example.cookare.mapper.RecipeNetworkMapper
import com.example.cookare.model.Ingredient
import com.example.cookare.model.Recipe
import com.example.cookare.model.loves
import com.example.cookare.network.RecipeSearchResponse
import com.example.cookare.ui.theme.BackgroundWhite
import com.example.cookare.ui.theme.green000
import com.example.cookare.ui.theme.green100
import com.example.cookare.ui.theme.green200
import com.example.cookare.viewModels.PostRecipeViewModel
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.concurrent.thread

@Composable
fun ListScreen(viewModel: PostRecipeViewModel) {
   /* val ingredient = Ingredient(
        name = "potato",
        num = 10,
        quantifier = "pieces"
    )
    val recipe = Recipe(
//        id = 9,
        title = "Test 6",
        content = "This is content -- test 6ashuaifhuefgtqigdyitaeydfgyfqdufhagdywfdyafdtyfywdguitcugqeyfdyafdyqfd",
        tags = 3,
        updateUser = 1,
        coverUrl = "",
        ingredients = listOf(ingredient)
    )

    Column() {
        Button(
            onClick = { viewModel.postRecipe(recipe) }
        ) {
            Text(text = "post recipe")
        }
//    val recipe1 = viewModel.resRecipe.value
//    recipe1.title?.let { Text(text = it) }
//    Log.d("PostRecipeResult", "ShowResult: ${recipe1.title}")
//
        Column() {
            val recipe2 = viewModel.resRecipeList.value
            for(r in recipe2){
                Log.d("PostRecipeListResult", "ShowResult: ${r.id}")
                r.id?.let { Text(text = it.toString()) }
            }

            Text(text = "in ListScreen")
        }

    }*/
    ShowRecipe()



}

@Composable
fun ShowRecipe() {
    Box(
        Modifier
            .fillMaxSize()
            .background(BackgroundWhite),
    ) {


        Column {
            androidx.compose.material.Text(
                modifier = Modifier.padding(20.dp, 20.dp, 28.dp, 0.dp),
                text = "Selected Recipe",
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium
            )
            Row(
                Modifier
                    .horizontalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp, vertical = 16.dp)
            )/* {
                val dogImages = remember { mutableStateOf(emptyList<String>()) }

                LaunchedEffect(Unit) {
                    val presenter = Recipe()
                    thread {
                        dogImages.value = presenter.coverUrl
                    }
                }

                for (dogImage in dogImages.value) {
                    StoryAvatar(
                        imageUrl = dogImage,
                        onClick = {
                            clickedDogImage.value = dogImage
                        }
                    )
                }
            }*/
            {
                for (love in loves) {
                    StoryAvatar(
                        imageUrl = love.cover_url,
                        onClick = {

                        }
                    )
                }
            }

            Divider()
        }


    }
}

@Composable
fun StoryAvatar(imageUrl: String, onClick: () -> Unit) {
    var clicked by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }
    Box(
        Modifier
            .padding(end = 8.dp)
            .border(
                width = 2.dp,
                brush = Brush.verticalGradient(listOf(green100, green200, green000)),
                shape = CircleShape
            )
            .padding(6.dp)
            .size(60.dp)
            .clip(CircleShape)
            .background(Color.LightGray)
            .clickable {
                clicked = true
                showDialog = true
            }
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
        )
        if (clicked and showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text(text = "Delete") },
                text = { Text(text = "Are you sure to remove this dish?") },
                buttons = {
                    Row() {
                        TextButton(onClick = { /*TODO*/ }) {
                            Text(text = "Yes")
                        }
                        TextButton(onClick = { /*TODO*/ }) {
                            Text(text = "Cancel")
                        }
                    }

                },
            modifier = Modifier.background(BackgroundWhite)

            )
        }



    }
}

