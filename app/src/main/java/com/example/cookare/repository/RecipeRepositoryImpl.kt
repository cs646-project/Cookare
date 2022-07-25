package com.example.cookare.repository

import com.example.cookare.mapper.IngredientNetworkMapper
import com.example.cookare.mapper.RecipeNetworkMapper
import com.example.cookare.model.*
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

    override suspend fun updatePlan(token: String, plan: Plan): String {
        recipeService.updatePlan(token, plan).data
        return "Successfully!"
    }

    override suspend fun getPlan(token: String, request: GetAllRecipe): List<Data> {
        val result = recipeService.getPlan(token, request).data
        val data = result.map {
            Data(
                recipeMapper.mapToModel(it.recipe),
                ingreMapper.toModelList(it.ingredients)
            )
        }
        return data
    }

    override suspend fun deletePlan(token: String, plan: Plan): String {
        recipeService.deletePlan(token, plan).data
        return "Successfully!"
    }

    override suspend fun getStock(token: String, request: GetAllRecipe): Map<String, Int> {
        return recipeService.getStock(token, request).data
    }

    override suspend fun addStock(token: String, stockMap: StockMap): String {
        recipeService.addStock(token, stockMap).data
        return "Successfully!"
    }

    override suspend fun updateStock(token: String, stockMap: StockMap): String {
        val result = recipeService.updateStock(token, stockMap).data
        return "Successfully!"
    }

    override suspend fun deleteStock(token: String, stockMap: StockMap): String {
        recipeService.deleteStock(token, stockMap).data
        return "Successfully!"
    }

    override suspend fun generateList(token: String, request: GetAllRecipe): Map<String, Int> {
        return recipeService.generateList(token, request).data
    }
}