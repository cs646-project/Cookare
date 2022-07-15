package com.bisfunc.Stock.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bisfunc.Stock.entity.Stock;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface StockMapper extends BaseMapper<Stock> {
}
