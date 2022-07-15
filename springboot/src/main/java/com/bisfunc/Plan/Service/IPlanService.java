package com.bisfunc.Plan.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bisfunc.Plan.entity.Plan;

import java.util.List;

public interface IPlanService extends IService<Plan> {
    List<Integer> getUserCurrentPlanRecipeIdList();

    void createPlan();
}
