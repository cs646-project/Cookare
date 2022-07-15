package com.example.cookare.repository

import com.example.cookare.model.Data
import com.example.cookare.model.GetAllRecipe
import com.example.cookare.model.Recipe
import com.example.cookare.model.SearchById

interface RecipeRepository {
    suspend fun postRecipe(token: String, recipe: Recipe) : Recipe
    suspend fun getAllRecipes(token: String, request: GetAllRecipe) : List<Data>
    suspend fun searchRecipeById(token: String, recipeIdList: SearchById) : List<Data>
    suspend fun deleteRecipeById(token: String, recipeId: Int)
}