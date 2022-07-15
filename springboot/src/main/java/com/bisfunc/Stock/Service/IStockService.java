package com.bisfunc.Stock.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bisfunc.Stock.entity.Stock;

import java.util.Map;

public interface IStockService extends IService<Stock> {
    void createStock();

    Map<String, Integer> getUserCurrentStockMap();
}
