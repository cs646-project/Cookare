package com.example.cookare.viewModels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cookare.model.GetAllRecipe
import com.example.cookare.model.Recipe
import com.example.cookare.model.SearchById
import com.example.cookare.repository.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class PostRecipeViewModel
@Inject
constructor(
    private val repository: RecipeRepository,
    private @Named("auth_token") val token: String,
) : ViewModel() {
    val resRecipe: MutableState<Recipe> = mutableStateOf(Recipe())
    val resRecipeList: MutableState<List<Recipe>> = mutableStateOf(listOf())
    val resRecipeByIdList: MutableState<List<Recipe>> = mutableStateOf(listOf())

    init {
//        viewModelScope.launch {
//            val res = repository.postRecipe(
//                token = token,
//                recipe = recipe
//            )
//            resRecipe.value = res
//        }

//        viewModelScope.launch {
//            val res = repository.searchRecipeById(
//                token = token,
//                recipeIdList = SearchById(listOf(5,6,7))
//            )
//            resRecipeList.value = res
//        }
        viewModelScope.launch {
            val res = repository.getAllRecipes(
                token = token,
                request = GetAllRecipe(null)
            )
            resRecipeList.value = res
        }
    }

    fun postRecipe(recipe: Recipe) {
        viewModelScope.launch {
            val res = repository.postRecipe(
                token = token,
                recipe = recipe
            )
            resRecipe.value = res
        }
    }

    fun searchById(list: List<Int>) {
        viewModelScope.launch {
            val res = repository.searchRecipeById(
                token = token,
                recipeIdList = SearchById(list)
            )
            resRecipeByIdList.value = res
        }
    }

    fun deletdById(recipeId: Int) {
        viewModelScope.launch {
            val res = repository.deleteRecipeById(
                token = token,
                recipeId = recipeId
            )
        }
    }
//
//    fun getAllRecipes(){
//        viewModelScope.launch {
//            val res = repository.getAllRecipes(
//                token = token,
//                request = GetAllRecipe(null)
//            )
//            resRecipeList.value = res
//        }
//    }
}