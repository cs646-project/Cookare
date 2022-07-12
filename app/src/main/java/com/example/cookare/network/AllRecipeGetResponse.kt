package com.example.cookare.network

import com.google.gson.annotations.SerializedName

data class AllRecipeGetResponse(
    @SerializedName("data")
    val data: List<DataNetworkEntity>,

    @SerializedName("code")
    val code: Int,

    @SerializedName("msg")
    val msg: String
)
