package com.bisfunc.Recipe.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bisfunc.Recipe.Mapper.IngredientsMapper;
import com.bisfunc.Recipe.entity.Ingredients;
import com.bisfunc.Recipe.entity.RecipeVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@Component
public class IngredientsService extends ServiceImpl<IngredientsMapper, Ingredients> implements IIngredientsService{
    @Resource
    IngredientsMapper ingredientsMapper;

    @Override
    public RecipeVo synRecipeVoIngredients(RecipeVo recipeVo, Integer recipeId) {
        List<Ingredients> ingredientsList = ingredientsMapper.selectList(new LambdaQueryWrapper<Ingredients>().eq(Ingredients::getRecipeId, recipeId));
        recipeVo.setIngredients(ingredientsList);
        return recipeVo;
    }

    @Override
    public Map<String, Integer> getNeedIngredientsFromRecipeIdList(List<Integer> recipeIdList) {
        List<Ingredients> needIngredientsList = ingredientsMapper.selectList(new LambdaQueryWrapper<Ingredients>().in(Ingredients::getRecipeId, recipeIdList));
        Map<String, Integer> retMap = new HashMap<>();
        for(Ingredients ingredients: needIngredientsList){
            retMap.put(ingredients.getName(), retMap.getOrDefault(ingredients.getName(), 0) + ingredients.getNum());
        }
        return retMap;
    }
}
