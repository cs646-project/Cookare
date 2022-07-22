package com.example.cookare.network

import com.google.gson.annotations.SerializedName

data class DataNetworkEntity(
    @SerializedName("recipe")
    var recipe: RecipeNetWorkEntity,

    @SerializedName("ingredients")
    var ingredients: List<IngredientNetWorkEntity>
)
