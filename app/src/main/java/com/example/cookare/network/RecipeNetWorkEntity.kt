package com.example.cookare.network

import com.example.cookare.model.Ingredient
import com.google.gson.annotations.SerializedName

data class RecipeNetWorkEntity(
    @SerializedName("id")
    var id: Int? = null,

    @SerializedName("title")
    var title: String? = null,

    @SerializedName("content")
    var content: String? = null,

    @SerializedName("tags")
    var tags: Int? = null,

    @SerializedName("updateUser")
    var updateUser: Int? = null,

    @SerializedName("coverUrl")
    var coverUrl: String? = null,

    @SerializedName("createTime")
    var createTime: String? = null,

    @SerializedName("updateTime")
    var updateTime: String? = null,

    @SerializedName("deleteFlg")
    var deleteFlg: String? = null,
)
