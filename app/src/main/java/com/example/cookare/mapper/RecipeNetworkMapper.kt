package com.example.cookare.mapper

import com.example.cookare.model.Recipe
import com.example.cookare.network.RecipeNetWorkEntity

class RecipeNetworkMapper: EntityMapper<RecipeNetWorkEntity, Recipe> {
    override fun mapToModel(entity: RecipeNetWorkEntity): Recipe {
        return Recipe(
            id = entity.id,
            title = entity.title,
            content = entity.content,
            tags = entity.tags,
            updateUser = entity.updateUser,
            coverUrl = entity.coverUrl,
        )
    }

    override fun mapFromModel(model: Recipe): RecipeNetWorkEntity {
        return RecipeNetWorkEntity(
            id = model.id,
            title = model.title,
            content = model.content,
            tags = model.tags,
            updateUser = model.updateUser,
            coverUrl = model.coverUrl,
        )
    }

    fun toModelList(initial: List<RecipeNetWorkEntity>): List<Recipe>{
        return initial.map{ mapToModel(it)}
    }

    fun fromModelList(initial: List<Recipe>): List<RecipeNetWorkEntity>{
        return initial.map{ mapFromModel(it)}
    }

}