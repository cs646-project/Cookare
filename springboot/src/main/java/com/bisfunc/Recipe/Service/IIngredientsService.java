package com.bisfunc.Recipe.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bisfunc.Recipe.entity.Ingredients;
import com.bisfunc.Recipe.entity.Recipe;
import com.bisfunc.Recipe.entity.RecipeVo;

public interface IIngredientsService extends IService<Ingredients> {
    RecipeVo synRecipeVoIngredients(RecipeVo recipeVo, Integer recipeId);
}
