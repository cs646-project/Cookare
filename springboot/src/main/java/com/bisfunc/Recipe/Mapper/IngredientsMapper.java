package com.bisfunc.Recipe.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bisfunc.Recipe.entity.Ingredients;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface IngredientsMapper extends BaseMapper<Ingredients> {

}
