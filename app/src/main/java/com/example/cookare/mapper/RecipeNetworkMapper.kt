package com.example.cookare.mapper

import com.example.cookare.model.Recipe
import com.example.cookare.network.RecipeNetWorkEntity

class RecipeNetworkMapper: EntityMapper<RecipeNetWorkEntity, Recipe> {

    override fun mapToModel(entity: RecipeNetWorkEntity): Recipe {
        return Recipe(
            id = entity.pk,
            title = entity.title,
            featuredImage = entity.featuredImage,
            rating = entity.rating,
            publisher = entity.publisher,
            sourceUrl = entity.sourceUrl,
            description = entity.description,
            cookingInstructions = entity.cookingInstructions,
            ingredients = entity.ingredients.orEmpty(),
            dateAdded = entity.dateAdded,
            dateUpdated = entity.dateUpdated,
        )
    }

    override fun mapFromModel(model: Recipe): RecipeNetWorkEntity {
        return RecipeNetWorkEntity(
            pk = model.id,
            title = model.title,
            featuredImage = model.featuredImage,
            rating = model.rating,
            publisher = model.publisher,
            sourceUrl = model.sourceUrl,
            description = model.description,
            cookingInstructions = model.cookingInstructions,
            ingredients = model.ingredients,
            dateAdded = model.dateAdded,
            dateUpdated = model.dateUpdated,
        )
    }

    fun toModelList(initial: List<RecipeNetWorkEntity>): List<Recipe>{
        return initial.map{ mapToModel(it)}
    }

    fun fromModelList(initial: List<Recipe>): List<RecipeNetWorkEntity>{
        return initial.map{ mapFromModel(it)}
    }

}