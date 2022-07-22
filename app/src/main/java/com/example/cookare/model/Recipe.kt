package com.example.cookare.model

data class Recipe(
    val id: Int? = null,
    val title: String? = null,
    val content: String? = null,
    val tags: Int? = null,
    val updateUser: Int? = null,
    val coverUrl: String? = null,
    val ingredients: List<Ingredient>? = listOf(),
)