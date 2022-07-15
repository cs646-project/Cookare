package com.bisfunc.Plan.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bisfunc.Plan.entity.Plan;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface PlanMapper extends BaseMapper<Plan> {
}
