package com.bisfunc.Recipe.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.bisfunc.Recipe.Mapper.IngredientsMapper;
import com.bisfunc.Recipe.Mapper.RecipeMapper;
import com.bisfunc.Recipe.Service.IIngredientsService;
import com.bisfunc.Recipe.Service.IRecipeService;
import com.bisfunc.Recipe.entity.Ingredients;
import com.bisfunc.Recipe.entity.Recipe;
import com.bisfunc.Recipe.entity.RecipeDto;
import com.bisfunc.Recipe.entity.RecipeVo;
import com.common.constants.Constants;
import com.common.constants.MsgConstants;
import com.common.domain.vo.Result;
import com.usrfunc.user.Service.ILoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/recipe")
public class RecipeController {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    ILoginService loginService;

    @Resource
    IRecipeService recipeService;

    @Resource
    RecipeMapper recipeMapper;

    @Resource
    IngredientsMapper ingredientsMapper;

    @Resource
    IIngredientsService ingredientsService;

    @SaCheckLogin
    @PostMapping("/updateRecipe")
    @ResponseBody
    public Result<?> updateRecipe(@RequestBody RecipeDto recipeDto) {
        Recipe old_recipe = null;
        //chk info completeness
        if (!recipeService.isNecessaryRecipeInfo(recipeDto)) {
            return Result.error(MsgConstants.ERROR.MISSING_INFO);
        }
        if (recipeDto.getUpdateUser() != loginService.getCurrentUserId()) {
            return Result.error(MsgConstants.ERROR.WRONG_USER);
        }

        //if is update an existing recipe, check if is the author
        if (recipeDto.getId() != null) {
            old_recipe = recipeMapper.selectById(recipeDto.getId());
            if (old_recipe == null) {
                return Result.error(MsgConstants.ERROR.INCONSISTENT_INFO);
            }
        }
        recipeService.htmlTransEscape(recipeDto);
        //update
        if (recipeDto.getId() == null) {
            old_recipe = new Recipe();
            //set default cover url
            //todo save default url as form of xml
            RecipeDto.SynDtoToRecipe(recipeDto, old_recipe);
            old_recipe.setCoverUrl("https://seopic.699pic.com/photo/50074/8934.jpg_wh1200.jpg");
            recipeMapper.insert(old_recipe);
        } else {
            RecipeDto.SynDtoToRecipe(recipeDto, old_recipe);
            recipeMapper.updateById(old_recipe);
        }
        if (recipeDto.getIngredients() != null) {
            ingredientsMapper.delete(new LambdaQueryWrapper<Ingredients>().eq(Ingredients::getRecipeId, old_recipe.getId()));
            for(Ingredients ingredients: recipeDto.getIngredients()){
                ingredients.setRecipeId(old_recipe.getId());
            }
            ingredientsService.saveBatch(recipeDto.getIngredients());
        }
        RecipeVo recipeVo = new RecipeVo();
        recipeVo.setRecipe(old_recipe);
//        recipeVo.setIngredients(ingredientsMapper.selectList(new LambdaQueryWrapper<Ingredients>().eq(Ingredients::getRecipeId, old_recipe.getId())));
        recipeVo.setIngredients(recipeDto.getIngredients());
        return Result.success(recipeVo);
    }

    @SaCheckLogin
    @PostMapping("/deleteRecipe")
    @ResponseBody
    public Result<?> deleteRecipe(@RequestParam("recipeId") Integer recipeId) {
        //exist?
        Recipe recipe = recipeMapper.selectById(recipeId);
        if (recipe == null) {
            return Result.error(MsgConstants.ERROR.NOT_EXIST);
        }
        //right user?
        if (loginService.getCurrentUserId() != recipe.getUpdateUser()) {
            return Result.error(MsgConstants.ERROR.WRONG_USER);
        }

        //delete
        recipeMapper.deleteById(recipe);
        ingredientsMapper.delete(new LambdaUpdateWrapper<Ingredients>().eq(Ingredients::getRecipeId, recipeId));
        return Result.success(Constants.SUCCESS);
    }

    @PostMapping("/searchRecipeById")
    @ResponseBody
    public Result<?> searchRecipeById(@RequestBody Map<String, Object> requestMap) {
        List<Integer> recipeIdList = (List<Integer>) requestMap.getOrDefault("recipeIdList", null);
        //exist?
        List<Recipe> recipeList = recipeMapper.selectBatchIds(recipeIdList);
        if (recipeList == null) {
            return Result.error(MsgConstants.ERROR.NOT_EXIST);
        }
        List<RecipeVo> recipeVoList = new ArrayList<>();
        for (Recipe recipe : recipeList) {
            recipeService.htmlTransUNEscape(recipe);
            RecipeVo recipeVo = new RecipeVo();
            recipeVo.setRecipe(recipe);
            ingredientsService.synRecipeVoIngredients(recipeVo, recipe.getId());
            recipeVoList.add(recipeVo);
        }
        return Result.success(recipeVoList);
    }

    @PostMapping("/searchRecipeByTitle")
    @ResponseBody
    public Result<?> searchRecipeByTitle(@RequestParam("recipeTitle") String title) {
        List<Recipe> resultList = null;

        return Result.success(resultList);
    }
}
