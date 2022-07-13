package com.example.cookare.activities

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.example.cookare.model.Recipe
import com.example.cookare.ui.theme.CookareTheme
import com.example.cookare.viewModels.PostRecipeViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class EditPostActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val searchId = Integer.parseInt(intent.getStringExtra("id"))
        Log.d("searchId", "recipeId $searchId" )

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
    Log.d("EditRecipeScreen", "recipeId---------------- $recipeId" )
    viewModel.searchById(listOf(recipeId))
    var recipes = viewModel.resRecipeByIdList.value
    Log.d("EditRecipeScreen", "recipeSize---------------- ${recipes.size}" )
    var recipe = if(recipes.isNotEmpty())  recipes[0] else null
    if (recipe != null) {
        Log.d("EditRecipeScreen", "recipe---------------- ${recipe.title}" )
    }

    var title by mutableStateOf(if(recipe != null) recipe.title else "")
    var content by mutableStateOf(if (recipe != null) recipe.content else "")
    var tags by mutableStateOf(recipe?.tags?.toString() ?: "")
    var updateUser by mutableStateOf(if(recipe != null) recipe.updateUser else "")
    var coverUrl by mutableStateOf(if(recipe != null) recipe.coverUrl else "")

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
                        onClick = {  },
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

            title?.let { it1 ->
                OutlinedTextField(
                    value = it1,
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
        //                label = { Text(text = "Title") },
        //                placeholder = { (if(recipe != null) recipe.title else "")?.let { Text(text = it) } },
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
        //                label = { Text(text = "Content") },
        //                placeholder = { (if(recipe != null) recipe.content else "")?.let { Text(text = it) }  },
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
//                label = { Text(text = "Tags") },
//                placeholder = { Text(text = recipe?.tags?.toString() ?: "")  },
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
        //                label = { Text(text = "Image url") },
        //                placeholder = { (if(recipe != null) recipe.coverUrl else "")?.let { Text(text = it) } },
                    onValueChange = {
                        coverUrl = it
                    }
                )
            }

//            title?.let {
//                OutlinedTextField(
//                    value = it,
//                    modifier = Modifier
//                        .padding(8.dp)
//                        .fillMaxWidth(),
//                    label = { Text(text = "Title") },
//                    placeholder = { Text(text = "") },
//                    onValueChange = {
////                        title = it
//                    }
//                )
//            }
//
//            content?.let {
//                OutlinedTextField(
//                    value = it,
//                    modifier = Modifier
//                        .padding(8.dp)
//                        .fillMaxWidth(),
//                    label = { Text(text = "Content") },
//                    placeholder = { Text(text = "") },
//                    onValueChange = {
////                        content = it
//                    }
//                )
//            }

//            OutlinedTextField(
//                value = tags,
//                modifier = Modifier
//                    .padding(8.dp)
//                    .fillMaxWidth(),
//                label = { Text(text = "Tags") },
//                placeholder = { Text(text = "") },
//                onValueChange = {
//                    tags = it
//                }
//            )

//            coverUrl?.let {
//                OutlinedTextField(
//                    value = it,
//                    modifier = Modifier
//                        .padding(8.dp)
//                        .fillMaxWidth(),
//                    label = { Text(text = "Image url") },
//                    placeholder = { Text(text = "") },
//                    onValueChange = {
////                        coverUrl = it
//                    }
//                )
//            }
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
                            1,
                            coverUrl,
                            null
                        )
                    )

//                    navController.navigate(ScreenRoute.HomeScreen.route)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
                    .height(50.dp)
                    .clip(CircleShape)
            ) {
                Text(text = "Edit")
//                for(r in recipes){
//                    Log.d("PostRecipeListResult", "ShowResult: ${r.id}")
//                    r.id?.let { Text(text = it.toString()) }
//                    r.title?.let { Text(text = it) }
//                }
//                Text(text = recipe.id.toString())
//                recipe.title?.let { Text(text = it) }
            }
        }
    }
}
