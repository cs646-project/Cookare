package com.example.cookare.mapper

import com.example.cookare.model.Ingredient
import com.example.cookare.model.Recipe
import com.example.cookare.network.IngredientNetWorkEntity
import com.example.cookare.network.RecipeNetWorkEntity

class IngredientNetworkMapper: EntityMapper<IngredientNetWorkEntity, Ingredient> {
    override fun mapToModel(entity: IngredientNetWorkEntity): Ingredient {
        return Ingredient(
            name = entity.name,
            num = entity.num,
            quantifier = entity.quantifier
        )
    }

    override fun mapFromModel(model: Ingredient): IngredientNetWorkEntity {
        return IngredientNetWorkEntity(
            name = model.name,
            num = model.num,
            quantifier = model.quantifier
        )
    }

    fun toModelList(initial: List<IngredientNetWorkEntity>): List<Ingredient>{
        return initial.map{ mapToModel(it)}
    }

    fun fromModelList(initial: List<Ingredient>): List<IngredientNetWorkEntity>{
        return initial.map{ mapFromModel(it)}
    }
}