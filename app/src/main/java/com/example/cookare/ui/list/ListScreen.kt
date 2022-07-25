package com.example.cookare.ui.list

import android.content.Context
import android.util.Log
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.amplifyframework.core.Amplify
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
import com.example.cookare.ui.home.EditPost
import com.example.cookare.ui.home.HomeScreen
import com.example.cookare.ui.home.PostTemplate
import com.example.cookare.ui.home.notification.NotificationScreen
import com.example.cookare.ui.profile.ProfileScreen
import com.example.cookare.ui.theme.*
import com.example.cookare.ui.upPress
import com.example.cookare.ui.utils.DEFAULT_RECIPE_IMAGE
import com.example.cookare.ui.utils.ScreenRoute
import com.example.cookare.ui.utils.loadPicture
import com.example.cookare.viewModels.ListGenerateViewModel
import com.example.cookare.viewModels.PlanViewModel
import com.example.cookare.viewModels.PostRecipeViewModel
import com.example.cookare.viewModels.StockViewModel
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import kotlin.concurrent.thread

@Composable
fun ListScreenNavigate() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = ScreenRoute.ListScreen.route) {
        composable(route = ScreenRoute.ListScreen.route) {
            ListScreen(hiltViewModel(), hiltViewModel(),navController)
        }
    }
}

@Composable
fun ListScreen(
    planViewModel: PlanViewModel,
    listGenerateViewModel: ListGenerateViewModel,
    navController: NavController
) {

    planViewModel.getPlan()
    val data = planViewModel.resPlanList.value
    val recipes = data.map { it.recipe }
//    val data = planViewModel.resPlanList?.value
//    val recipes = data?.map { it.recipe }

    val shoppingList = listGenerateViewModel.resListGenerate.value

    var showList by remember {
        mutableStateOf(false)
    }

    Scaffold(floatingActionButton = {
        FloatingActionButton(
            backgroundColor = green000,
            onClick = {
            listGenerateViewModel.generateList()
            showList = true
        },
        ) {
            androidx.compose.material.Text("GET", fontSize = 16.sp, color = BackgroundWhite)
        }
    }) {

        Column() {
            Box(
                Modifier
                    .background(BackgroundWhite),
            ) {


                Column {
                    androidx.compose.material.Text(
                        modifier = Modifier.padding(25.dp, 20.dp, 28.dp, 0.dp),
                        text = "Selected Recipe",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                    androidx.compose.material.Text(
                        modifier = Modifier.padding(25.dp, 10.dp, 28.dp, 10.dp),
                        text = "Here are some recipes you selected.",
                        fontSize = 15.sp,
                        color = Gray100
                    )
                    Divider()

//                    if (data != null) {
//                        if (data.isNotEmpty()) {
//                            LazyColumn(
//                                modifier = Modifier.heightIn(max = 400.dp)
//                            )
//                            {
//                                items(1) {
//                                    if (recipes != null) {
//                                        for(recipe in recipes){
//                                            RecipeArea(recipe = recipe,viewModel=planViewModel)
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                    }
                    if (data.isNotEmpty()) {
                        LazyColumn(
                            modifier = Modifier.heightIn(max = 180.dp)
                        )
                        {
                            items(recipes) { recipe ->
                                RecipeArea(recipe = recipe, viewModel = planViewModel, navController = navController)
                            }
                        }
                    }else{

                                AsyncImage(
                                    model = "https://i.postimg.cc/pLfDhk43/select-instruction.jpg",
                                    contentDescription = "",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                )


                    }

                    Divider()
                    androidx.compose.material.Text(
                        modifier = Modifier.padding(25.dp, 20.dp, 28.dp, 10.dp),
                        text = "My List",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )

                    if (showList) {

                        LazyColumn(
                        )
                        {
                            items(1) {
                                AsyncImage(
                                    model = "https://i.postimg.cc/ZqhntcMK/shopping-list.jpg",
                                    contentDescription = "",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                )

                                for (entry in shoppingList.entries.iterator()) {
                                    CardList(entry.key, entry.value)
                                }
                                AsyncImage(
                                    model = "https://i.postimg.cc/dtfpZ5Ms/shopping-bottom.jpg",
                                    contentDescription = "",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                )
                            }
                        }

                    }
                    else{
                        AsyncImage(
                            model = "https://i.postimg.cc/fRTywK7w/list-generation.jpg",
                            contentDescription = "",
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                    }

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
fun RecipeArea(recipe: Recipe, viewModel: PlanViewModel, navController: NavController) {
    val context = LocalContext.current

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
                StoryAvatar(recipe = recipe, context = context)
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
                            recipe.id?.let {
                                viewModel.deletePlan(it)
                            }
                            showDeleteDialog = false
                            navController.navigate(ScreenRoute.ListScreen.route)
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
fun StoryAvatar(
    recipe: Recipe,
    context: Context
) {

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
        recipe.coverUrl?.let { url ->
            val downloadedImage = downloadPhoto(recipe.coverUrl!!, context)
            Image(
                painter = rememberAsyncImagePainter(downloadedImage),
                contentDescription = "Recipe Featured Image",
                contentScale = ContentScale.Crop,
            )
        }
    }
}

@Composable
fun CardList(
    key: String,
    value: Int
) {
    Card(
        backgroundColor = Color.White,
      //  border = BorderStroke(2.dp, green900),
        shape = RoundedCornerShape(0.dp),
        modifier = Modifier
            .padding(horizontal = 85.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp, vertical = 20.dp),
        ) {
            val isChecked = remember {mutableStateOf(false)}
            Checkbox(checked = isChecked.value ,
                colors = CheckboxDefaults.colors(
                            checkedColor = green700,
                            uncheckedColor = green500),
                onCheckedChange = {isChecked.value=it })


            androidx.compose.material.Text(
                text = key,
                style = MaterialTheme.typography.h6,
                modifier = Modifier
                    .padding(start = 20.dp)
                    .weight(2f)
            )


            androidx.compose.material.Text(
                text = value.toString(),
                style = MaterialTheme.typography.body1,
                modifier = Modifier.weight(1f)
            )

        }
        Divider()


    }
}

private fun downloadPhoto(
    coverUrl: String,
    context: Context
): File {
    val photoKey = "$coverUrl.png"
    val filePath = "${context.filesDir}/${photoKey}"

    Log.i("downloadPhoto", "download photoKey is: $photoKey")

    if (!fileIsExists(filePath)) {
        val localFile = File(filePath)
        Amplify.Storage.downloadFile(
            photoKey,
            localFile,
            { },
            { Log.e("downloadPhoto", "Failed download", it) }
        )
    }

    return File(filePath)
}

private fun fileIsExists(filePath: String): Boolean {
    try {
        val f = File(filePath)
        if (!f.exists()) {
            return false
        }
    } catch (e: Exception) {
        return false
    }
    return true
}