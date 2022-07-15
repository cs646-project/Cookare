package com.bisfunc.Plan.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bisfunc.Plan.entity.Plan;
import com.bisfunc.Plan.mapper.PlanMapper;
import com.usrfunc.user.Service.ILoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Component
@Service
@Slf4j
public class PlanService extends ServiceImpl<PlanMapper, Plan> implements IPlanService{
    @Resource
    PlanMapper planMapper;

    @Resource
    ILoginService loginService;

    @Override
    public List<Integer> getUserCurrentPlanRecipeIdList() {
        Plan userPlan = planMapper.selectOne(new LambdaQueryWrapper<Plan>().eq(Plan::getUpdateUser, loginService.getCurrentUserId()));
        return Plan.jsonStringToPlanList(userPlan.getRecipeIdList());
    }

    @Override
    public void createPlan() {
        Plan plan = new Plan();
        planMapper.insert(plan);
    }
}
