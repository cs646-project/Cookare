package com.example.cookare.network

import com.google.gson.annotations.SerializedName

data class StockResponse(
    @SerializedName("data")
    val data: Map<String, Int>,

    @SerializedName("code")
    val code: Int,

    @SerializedName("msg")
    val msg: String
)