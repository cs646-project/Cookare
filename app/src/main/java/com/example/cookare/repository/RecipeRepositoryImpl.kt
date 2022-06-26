package com.example.cookare.repository

import com.example.cookare.mapper.RecipeNetworkMapper
import com.example.cookare.model.Recipe
import com.example.cookare.network.RecipeService

class RecipeRepositoryImpl(
    private val recipeService: RecipeService,
    private val mapper: RecipeNetworkMapper
): RecipeRepository {
    override suspend fun search(token: String, page: Int, query: String): List<Recipe> {
        val result = recipeService.search(token, page, query).recipes
        return mapper.toModelList(result)
    }

    override suspend fun get(token: String, id: Int): Recipe {
        return mapper.mapToModel(recipeService.get(token, id))
    }
}