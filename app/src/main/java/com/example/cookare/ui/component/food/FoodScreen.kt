package com.example.cookare.ui.component.food

import RecipeCard
import android.util.Log
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cookare.ui.component.home.UserStateViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalAnimationApi::class,
    ExperimentalMaterialApi::class)
@Composable
fun FoodScreen(viewModel: FoodScreenViewModel) {
    val recipes = viewModel.recipes.value
    
    LazyColumn {
        itemsIndexed(
            items = recipes
        ){
            index, recipe ->
            RecipeCard(recipe = recipe, onClick = {})
        }
    }
}