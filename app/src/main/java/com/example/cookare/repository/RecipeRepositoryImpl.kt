package com.example.cookare.repository

import com.example.cookare.mapper.IngredientNetworkMapper
import com.example.cookare.mapper.RecipeNetworkMapper
import com.example.cookare.model.Data
import com.example.cookare.model.GetAllRecipe
import com.example.cookare.model.Recipe
import com.example.cookare.model.SearchById
import com.example.cookare.network.RecipeService

class RecipeRepositoryImpl(
    private val recipeService: RecipeService,
    private val recipeMapper: RecipeNetworkMapper,
    private val ingreMapper: IngredientNetworkMapper
) : RecipeRepository {
    override suspend fun postRecipe(token: String, recipe: Recipe): Recipe {
        val result = recipeService.postRecipe(token, recipe).data
        return recipeMapper.mapToModel(result.recipe)
    }

    override suspend fun getAllRecipes(token: String, request: GetAllRecipe): List<Data> {
        val result = recipeService.getAllRecipes(token, request).data
        val data = result.map {
            Data(
                recipeMapper.mapToModel(it.recipe),
                ingreMapper.toModelList(it.ingredients)
            )
        }
        return data
    }

    override suspend fun searchRecipeById(token: String, recipeIdList: SearchById): List<Data> {
        val result = recipeService.searchRecipeById(token, recipeIdList).data
        val data = result.map {
            Data(
                recipeMapper.mapToModel(it.recipe),
                ingreMapper.toModelList(it.ingredients)
            )
        }
        return data
    }

    override suspend fun deleteRecipeById(token: String, recipeId: Int) {
        recipeService.deleteRecipeById(token, recipeId)
    }
}