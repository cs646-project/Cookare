package com.example.cookare.repository

import android.util.Log
import com.example.cookare.mapper.RecipeNetworkMapper
import com.example.cookare.model.GetAllRecipe
import com.example.cookare.model.Recipe
import com.example.cookare.model.SearchById
import com.example.cookare.network.AllRecipeGetResponse
import com.example.cookare.network.RecipeNetWorkEntity
import com.example.cookare.network.RecipeSearchResponse
import com.example.cookare.network.RecipeService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecipeRepositoryImpl(
    private val recipeService: RecipeService,
    private val mapper: RecipeNetworkMapper
): RecipeRepository {
    override suspend fun postRecipe(token: String, recipe: Recipe): Recipe {
        val result = recipeService.postRecipe(token, recipe).data
        return mapper.mapToModel(result.recipe)
    }

    override suspend fun getAllRecipes(token: String, request: GetAllRecipe): List<Recipe> {
        val result = recipeService.getAllRecipes(token, request).data
        return result.map { mapper.mapToModel(it.recipe) }
    }

    override suspend fun searchRecipeById(token: String, recipeIdList: SearchById): List<Recipe> {
        val result = recipeService.searchRecipeById(token, recipeIdList).data
        return result.map { mapper.mapToModel(it.recipe) }
    }
}