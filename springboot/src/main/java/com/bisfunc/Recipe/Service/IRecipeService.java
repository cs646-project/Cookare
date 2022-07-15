package com.bisfunc.Recipe.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bisfunc.Recipe.entity.Ingredients;
import com.bisfunc.Recipe.entity.Recipe;
import com.bisfunc.Recipe.entity.RecipeDto;
import com.bisfunc.Recipe.entity.RecipeVo;

import java.util.List;

public interface IRecipeService extends IService<Recipe> {
    boolean isNecessaryRecipeInfo(RecipeDto recipeDto);

    RecipeDto htmlTransEscape(RecipeDto recipe);

    Recipe htmlTransUNEscape(Recipe recipe);

    List<RecipeVo> getRecipeVoFromRecipeList(List<Recipe> recipeList);
}
