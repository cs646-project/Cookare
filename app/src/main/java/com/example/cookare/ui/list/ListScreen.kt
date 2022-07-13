package com.example.cookare.ui.list

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.cookare.model.Ingredient
import com.example.cookare.model.Recipe
import com.example.cookare.viewModels.PostRecipeViewModel

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
//            val recipe2 = viewModel.resRecipeList.value
//            for(r in recipe2){
//                Log.d("PostRecipeListResult", "ShowResult: ${r.id}")
//                r.id?.let { Text(text = it.toString()) }
//            }
//            val list: List<Int> = listOf(55,10)
//            viewModel.searchById(list)

            val recipe2 = viewModel.resRecipeByIdList.value
            for(r in recipe2){
                Log.d("PostRecipeListResult", "ShowResult: ${r.id}")
                r.id?.let { Text(text = it.toString()) }
                r.title?.let { Text(text = it) }
            }



            Text(text = "in ListScreen")
        }

    }


}