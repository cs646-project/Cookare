package com.bisfunc.Plan.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.bisfunc.Plan.Service.IPlanService;
import com.bisfunc.Plan.entity.Plan;
import com.bisfunc.Plan.mapper.PlanMapper;
import com.bisfunc.Recipe.Mapper.IngredientsMapper;
import com.bisfunc.Recipe.Mapper.RecipeMapper;
import com.bisfunc.Recipe.Service.IRecipeService;
import com.bisfunc.Recipe.entity.Ingredients;
import com.bisfunc.Recipe.entity.Recipe;
import com.bisfunc.Recipe.entity.RecipeVo;
import com.bisfunc.Stock.Service.IStockService;
import com.common.domain.vo.Result;
import com.usrfunc.user.Service.ILoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/plan")
public class PlanController {
    @Resource
    PlanMapper planMapper;

    @Resource
    IPlanService planService;

    @Resource
    ILoginService loginService;

    @Resource
    IStockService stockService;

    @Resource
    IngredientsMapper ingredientsMapper;

    @Resource
    RecipeMapper recipeMapper;

    @Resource
    IRecipeService recipeService;

    @SaCheckLogin
    @PostMapping("/updatePlan")
    @ResponseBody
    public Result<?> updatePlan(@RequestBody Map<String, Object> objectMap) {
        List<Integer> planList = (List<Integer>) objectMap.get("planList");
        Plan userPlan = planMapper.selectOne(new LambdaQueryWrapper<Plan>()
                .eq(Plan::getUpdateUser, loginService.getCurrentUserId()));
        String jsonString = Plan.planListToJsonString(planList);
        userPlan.setRecipeIdList(jsonString);
        planMapper.updateById(userPlan);
        return Result.success(Plan.jsonStringToPlanList(userPlan.getRecipeIdList()));
    }

    @SaCheckLogin
    @PostMapping("/getPlan")
    @ResponseBody
    public Result<?> getPlan(@RequestBody Map<String, Object> objectMap) {
        List<Integer> recipeIdList = planService.getUserCurrentPlanRecipeIdList();
        List<Recipe> recipeList = recipeIdList.size() != 0 ? recipeMapper.selectList(new LambdaQueryWrapper<Recipe>().in(Recipe::getId, recipeIdList)) : new ArrayList<>();
        List<RecipeVo> recipeVoList = recipeService.getRecipeVoFromRecipeList(recipeList);
        return Result.success(recipeVoList);
    }

    @SaCheckLogin
    @PostMapping("/generateBuyList")
    @ResponseBody
    public Result<?> generateBuyList(@RequestBody Map<String, Object> objectMap) {
        Map<String, Integer> currentStockMap = stockService.getUserCurrentStockMap();
        List<Integer> currentPlanRecipeIdList = planService.getUserCurrentPlanRecipeIdList();
        List<Ingredients> currentPlanRecipeIngredients = currentPlanRecipeIdList.size() != 0 ? ingredientsMapper.selectList(new LambdaQueryWrapper<Ingredients>()
                .in(Ingredients::getRecipeId, currentPlanRecipeIdList)) : new ArrayList<>();
        Map<String, Integer> needStockMap = new HashMap<>();
        Map<String, Integer> retMap = new HashMap<>();
        for (Ingredients ingredients : currentPlanRecipeIngredients) {
            String key = ingredients.getName();
            int num = ingredients.getNum();
            needStockMap.put(key, needStockMap.getOrDefault(key, 0) + num);
        }

        for (Map.Entry<String, Integer> entry : needStockMap.entrySet()) {
            int currentStockVal = currentStockMap.getOrDefault(entry.getKey(), 0);
            if (currentStockVal < entry.getValue()) {
                retMap.put(entry.getKey(), entry.getValue() - currentStockVal);
            }
        }
        return Result.success(retMap);
    }
}
