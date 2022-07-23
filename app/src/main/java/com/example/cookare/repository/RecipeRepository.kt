package com.example.cookare.repository

import com.example.cookare.model.*

interface RecipeRepository {
    // recipe
    suspend fun postRecipe(token: String, recipe: Recipe) : Recipe
    suspend fun getAllRecipes(token: String, request: GetAllRecipe) : List<Data>
    suspend fun searchRecipeById(token: String, recipeIdList: SearchById) : List<Data>
    suspend fun deleteRecipeById(token: String, recipeId: Int)

    // plan
    suspend fun updatePlan(token: String, plan: Plan) : String
    suspend fun getPlan(token: String, request: GetAllRecipe) : List<Data>
    suspend fun deletePlan(token: String, plan: Plan) : String

    // stock
    suspend fun getStock(token: String, request: GetAllRecipe) : Map<String,Int>
    suspend fun addStock(token: String, stockMap: StockMap) : String
    suspend fun updateStock(token: String, stockMap: StockMap) : String
    suspend fun deleteStock(token: String, stockMap: StockMap) : String

    // list
    suspend fun generateList(token: String, request: GetAllRecipe) : Map<String,Int>
}