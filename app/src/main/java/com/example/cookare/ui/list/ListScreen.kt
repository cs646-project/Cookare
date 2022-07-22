package com.example.cookare.ui.list

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.cookare.R
import com.example.cookare.ui.components.CookareDivider
import com.example.cookare.mapper.EntityMapper
import com.example.cookare.mapper.RecipeNetworkMapper
import com.example.cookare.model.Ingredient
import com.example.cookare.model.Recipe
import com.example.cookare.model.loves
import com.example.cookare.network.RecipeSearchResponse
import com.example.cookare.ui.food.RecipeCard
import com.example.cookare.ui.food.data.Todo
import com.example.cookare.ui.theme.*
import com.example.cookare.ui.utils.DEFAULT_RECIPE_IMAGE
import com.example.cookare.ui.utils.ScreenRoute
import com.example.cookare.ui.utils.loadPicture
import com.example.cookare.viewModels.PlanViewModel
import com.example.cookare.viewModels.PostRecipeViewModel
import com.example.cookare.viewModels.StockViewModel
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.concurrent.thread

@Composable
fun ListScreen(
    viewModel: PlanViewModel) {

    viewModel.getPlan()
    val data = viewModel.resPlanList.value
    val recipes = data.map { it.recipe }


    Scaffold(floatingActionButton = {
        FloatingActionButton(backgroundColor = green000,onClick = { /*TODO*/ }) {
            androidx.compose.material.Text("GET", fontSize = 16.sp, color = BackgroundWhite)
        }
    }){

        Column() {
            Box(
                Modifier
                    .background(BackgroundWhite),
            ) {


                Column {
                    androidx.compose.material.Text(
                        modifier = Modifier.padding(25.dp, 20.dp, 28.dp, 0.dp),
                        text = "Selected Recipe",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium
                    )
                    androidx.compose.material.Text(
                        modifier = Modifier.padding(25.dp, 10.dp, 28.dp, 0.dp),
                        text = "Here are some recipes you selected.",
                        fontSize = 15.sp,
                        color= Gray100
                    )
                    androidx.compose.material.Text(
                        modifier = Modifier.padding(25.dp, 0.dp, 28.dp, 10.dp),
                        text = "Click GET, and generate your list!",
                        fontSize = 15.sp,
                        color= Gray100
                    )
                    Divider()

                    if (data.isNotEmpty()) {
                        LazyColumn(
                        )
                        {
                            items(recipes) { recipe ->
                                RecipeArea(recipe = recipe,viewModel=viewModel)
                            }
                        }
                    }

                    Divider()
                    androidx.compose.material.Text(
                        modifier = Modifier.padding(25.dp, 20.dp, 28.dp, 0.dp),
                        text = "Your List",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium
                    )
                    CardList()
                    CardList()
                    CardList()
                    /*androidx.compose.material.Text(
                        modifier = Modifier.padding(20.dp, 20.dp, 28.dp, 0.dp),
                        text = "Here is your list!",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium
                    )*/

                }


            }


        }
    }



}

@Composable
fun RecipeArea(recipe:Recipe,viewModel: PlanViewModel) {
    var showDeleteDialog by remember { mutableStateOf(false) }
    Column(Modifier.padding(24.dp, 2.dp, 24.dp, 2.dp)) {
        Surface(
            Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color.White)
                .padding(8.dp)

        ) {
            Row(Modifier.height(IntrinsicSize.Max)) {
                StoryAvatar(recipe = recipe)
                recipe.title?.let { title ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp)
                    ) {
                        androidx.compose.material.Text(
                            text = title,
                            modifier = Modifier
                                .fillMaxWidth(0.85f)
                                .wrapContentWidth(Alignment.Start),
                            fontSize = 18.sp
                        )
                        androidx.compose.material3.OutlinedButton(
                            onClick = {
                                recipe.id?.let { viewModel.deletePlan(it) }
                                showDeleteDialog = true
                            },
                            modifier = Modifier
                                .size(40.dp)
                                .padding(5.dp),
                            shape = CircleShape,
                            border = BorderStroke(1.5.dp, green100),
                            contentPadding = PaddingValues(0.dp),
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Delete,
                                contentDescription = "Delete",
                                Modifier

                                    .size(26.dp), tint = Color.Black
                            )
                        }
//                    )
//                    Icon(
//                        imageVector = Icons.Default.FavoriteBorder,
//                        contentDescription = "like",
//                        modifier = Modifier
//                            .align(Alignment.CenterVertically)
//                    )
                    }
                }

            }
        }
    }
    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { androidx.compose.material3.Text(text = "Delete") },
            text = { androidx.compose.material3.Text(text = "Are you sure to delete this dish?") },
            buttons = {
                Row() {
                    TextButton(
                        onClick = {
                            showDeleteDialog = false
                        }
                    ) {
                        androidx.compose.material3.Text(text = "Yes")
                    }
                    TextButton(
                        onClick = { showDeleteDialog = false }
                    ) {
                        androidx.compose.material3.Text(text = "Cancel")
                    }
                }

            },
            modifier = Modifier.background(BackgroundWhite)
        )
    }
}

@Composable
fun StoryAvatar(recipe: Recipe) {

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

    ) {
        /*  AsyncImage(
             model = imageUrl,
             contentDescription = null,
             contentScale = ContentScale.Crop,
         )*/
        recipe.coverUrl?.let { url ->
            val image = loadPicture(url = url, defaultImage = DEFAULT_RECIPE_IMAGE).value
            image?.let { img ->
                Image(
                    bitmap = img.asImageBitmap(),
                    contentDescription = "Recipe Featured Image",
                    contentScale = ContentScale.Crop,
                )
            }
        }
        /* if (clicked and showDialog) {
             AlertDialog(
                 onDismissRequest = { showDialog = false },
                 title = { Text(text = "Delete") },
                 text = { Text(text = "Are you sure to remove this dish?") },
                 buttons = {
                     Row() {
                         TextButton(onClick = { /*TODO*/ }) {
                             Text(text = "Yes")
                         }
                         TextButton(onClick = {  }) {
                             Text(text = "Cancel")
                         }
                     }

                 },
                 modifier = Modifier.background(BackgroundWhite)

             )
         }*/


    }
}

@Composable
fun CardList() {
    Card(
        backgroundColor = green200,
        modifier = Modifier
            .padding(horizontal = 16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 15.dp, vertical = 5.dp),
        ) {
            Checkbox(checked = true/*TODO*/, onCheckedChange = {/*TODO*/  })


                androidx.compose.material.Text(
                    text = "Item",/*TODO*/
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier.padding(start = 15.dp).weight(1f)
                )


                    androidx.compose.material.Text(
                        text ="number",/*TODO*/
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier.weight(1f)
                    )



            IconButton(
                onClick = {
                    /*TODO*/
                }
            ) {
                Icon(imageVector = Icons.Default.Clear, contentDescription = null)
            }
        }
        Divider()


    }
}