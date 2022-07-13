package com.example.cookare.network

import com.google.gson.annotations.SerializedName

data class IngredientNetWorkEntity(
    @SerializedName("id")
    var id: Int? = null,

    @SerializedName("name")
    var name: String? = null,

    @SerializedName("recipeId")
    var recipeId: Int? = null,

    @SerializedName("num")
    var num: Int? = null,

    @SerializedName("quantifier")
    var quantifier: String? = null,
)
