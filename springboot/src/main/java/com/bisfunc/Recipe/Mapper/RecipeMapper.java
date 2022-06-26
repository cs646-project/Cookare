package com.bisfunc.Recipe.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bisfunc.Recipe.entity.Recipe;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface RecipeMapper extends BaseMapper<Recipe> {
}
