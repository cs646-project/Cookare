package com.example.cookare.network

import com.google.gson.annotations.SerializedName

data class RecipeSearchResponse(
    @SerializedName("data")
    val data: DataNetworkEntity
)