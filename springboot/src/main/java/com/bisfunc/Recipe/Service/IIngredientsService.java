package com.bisfunc.Recipe.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bisfunc.Recipe.entity.Ingredients;
import com.bisfunc.Recipe.entity.RecipeVo;

import java.util.List;
import java.util.Map;

public interface IIngredientsService extends IService<Ingredients> {
    RecipeVo synRecipeVoIngredients(RecipeVo recipeVo, Integer recipeId);

    Map<String, Integer> getNeedIngredientsFromRecipeIdList(List<Integer> recipeIdList);
}
