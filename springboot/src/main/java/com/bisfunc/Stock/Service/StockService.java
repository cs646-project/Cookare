package com.bisfunc.Stock.Service;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bisfunc.Stock.Mapper.StockMapper;
import com.bisfunc.Stock.entity.Stock;
import com.usrfunc.user.Service.ILoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@Component
public class StockService extends ServiceImpl<StockMapper, Stock> implements IStockService{
    @Resource
    StockMapper stockMapper;

    @Resource
    ILoginService loginService;

    @SaCheckLogin
    @Override
    public void createStock() {
        Stock stock = new Stock();
        stockMapper.insert(stock);
    }

    @Override
    public Map<String, Integer> getUserCurrentStockMap() {
        Stock stock = stockMapper.selectOne(new LambdaQueryWrapper<Stock>().eq(Stock::getUpdateUser, loginService.getCurrentUserId()));
        return Stock.jsonStringToStockMap(stock.getStock());
    }
}
