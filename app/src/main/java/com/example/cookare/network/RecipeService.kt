package com.example.cookare.network

import com.example.cookare.model.GetAllRecipe
import com.example.cookare.model.Recipe
import com.example.cookare.model.SearchById
import retrofit2.Call
import retrofit2.http.*

interface RecipeService {
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
}