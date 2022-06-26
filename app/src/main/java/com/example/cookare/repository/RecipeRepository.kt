package com.example.cookare.repository

import com.example.cookare.model.Recipe

interface RecipeRepository {
    suspend fun search(token: String, page: Int, query: String): List<Recipe>
    suspend fun get(token:String, id: Int): Recipe
}