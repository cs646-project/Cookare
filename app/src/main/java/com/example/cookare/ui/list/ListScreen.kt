package com.example.cookare.ui.list

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.cookare.mapper.EntityMapper
import com.example.cookare.mapper.RecipeNetworkMapper
import com.example.cookare.model.Ingredient
import com.example.cookare.model.Recipe
import com.example.cookare.network.RecipeSearchResponse
import com.example.cookare.viewModels.PostRecipeViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun ListScreen(viewModel: PostRecipeViewModel) {
    val ingredient = Ingredient(
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

    }


}