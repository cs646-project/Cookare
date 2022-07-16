package com.example.cookare.network

import com.google.gson.annotations.SerializedName

data class PlanGetResponse(
    @SerializedName("data")
    val data: List<Int>,

    @SerializedName("code")
    val code: Int,

    @SerializedName("msg")
    val msg: String
)
