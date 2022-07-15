package com.bisfunc.Stock.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.bisfunc.Stock.Mapper.StockMapper;
import com.bisfunc.Stock.Service.IStockService;
import com.bisfunc.Stock.entity.Stock;
import com.common.domain.vo.Result;
import com.usrfunc.user.Service.ILoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/stock")
public class StockController {
    @Resource
    IStockService stockService;

    @Resource
    StockMapper stockMapper;

    @Resource
    ILoginService loginService;

    @SaCheckLogin
    @PostMapping("/updateStock")
    @ResponseBody
    public Result<?> updateStock(@RequestBody Map<String, Object> objectMap){
        Map<String, Integer> stockMap = (Map<String, Integer>) objectMap.get("stockMap");
        Stock userStock = stockMapper.selectOne(new LambdaQueryWrapper<Stock>()
                .eq(Stock::getUpdateUser, loginService.getCurrentUserId()));
        String jsonString = Stock.stockMapToJsonString(stockMap);
        userStock.setStock(jsonString);
        stockMapper.updateById(userStock);
        return Result.success(Stock.jsonStringToStockMap(userStock.getStock()));
    }

    @SaCheckLogin
    @PostMapping("/getStock")
    @ResponseBody
    public Result<?> getStock(@RequestBody Map<String, Object> objectMap){
        return Result.success(stockService.getUserCurrentStockMap());
    }


}
