package com.bisfunc.Recipe.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bisfunc.Recipe.Mapper.IngredientsMapper;
import com.bisfunc.Recipe.Mapper.RecipeMapper;
import com.bisfunc.Recipe.entity.Ingredients;
import com.bisfunc.Recipe.entity.Recipe;
import com.bisfunc.Recipe.entity.RecipeDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
@Slf4j
@Component
public class RecipeService extends ServiceImpl<RecipeMapper, Recipe> implements IRecipeService{
    @Resource
    RecipeMapper recipeMapper;

    @Resource
    IngredientsMapper ingredientsMapper;

    @Override
    public boolean isNecessaryRecipeInfo(RecipeDto recipeDto) {
        if (recipeDto.getUpdateUser() == null ||
                recipeDto.getUpdateUser().equals("") ||
                recipeDto.getContent() == null ||
                recipeDto.getTitle() == null){
            return false;
        }
        return true;
    }

    public RecipeDto htmlTransEscape(RecipeDto recipe) {
        if(recipe!= null && recipe.getContent() != null){
            recipe.setContent(HtmlUtils.htmlEscapeHex(recipe.getContent()));
        }
        return recipe;
    }

    public Recipe htmlTransUNEscape(Recipe recipe) {
        if(recipe!= null && recipe.getContent() != null){
            recipe.setContent(HtmlUtils.htmlUnescape(recipe.getContent()));
        }
        return recipe;
    }

}
