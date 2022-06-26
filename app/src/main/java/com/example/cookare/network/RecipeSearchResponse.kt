package com.example.cookare.network

import com.google.gson.annotations.SerializedName

data class RecipeSearchResponse(

    @SerializedName("count")
    val count: Int,

    @SerializedName("results")
    val recipes: List<RecipeNetWorkEntity>
)