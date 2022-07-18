package com.example.cookare.network

import com.example.cookare.model.*
import retrofit2.Call
import retrofit2.http.*

interface RecipeService {
    // recipe
    @POST("recipe/updateRecipe")
    suspend fun postRecipe(
        @Header("Cookie") token:String,
        @Body recipe: Recipe
    ): RecipeSearchResponse

    @POST("recipe/getSelfRecipeList")
    suspend fun getAllRecipes(
        @Header("Cookie") token:String,
        @Body request: GetAllRecipe
    ): AllRecipeGetResponse

    @POST("recipe/searchRecipeById")
    @Headers("Content-type: application/json")
    suspend fun searchRecipeById(
        @Header("Cookie") token:String,
        @Body request: SearchById
    ): AllRecipeGetResponse

    @POST("recipe/deleteRecipe")
    @Headers("Content-type: application/json")
    suspend fun deleteRecipeById(
        @Header("Cookie") token:String,
        @Query("recipeId") recipeId: Int
    )

    // plan
    @POST("plan/updatePlan")
    @Headers("Content-type: application/json")
    suspend fun updatePlan(
        @Header("Cookie") token:String,
        @Body plan: Plan
    ): PlanGetResponse

    @POST("plan/updatePlan")
    @Headers("Content-type: application/json")
    suspend fun deletePlan(
        @Header("Cookie") token:String,
        @Body plan: Plan
    ): PlanGetResponse

    @POST("plan/getPlan")
    suspend fun getPlan(
        @Header("Cookie") token:String,
        @Body request: GetAllRecipe
    ): AllRecipeGetResponse

    // stock
    @POST("stock/getStock")
    suspend fun getStock(
        @Header("Cookie") token:String,
        @Body request: GetAllRecipe
    ): StockResponse

    @POST("stock/updateStock")
    suspend fun addStock(
        @Header("Cookie") token:String,
        @Body stockMap: StockMap
    ): StockResponse

    @POST("stock/updateStock")
    suspend fun deleteStock(
        @Header("Cookie") token:String,
        @Body stockMap: StockMap
    ): StockResponse

    // list
    @POST("plan/generateBuyList")
    suspend fun generateList(
        @Header("Cookie") token:String,
        @Body request: GetAllRecipe
    ): StockResponse
}