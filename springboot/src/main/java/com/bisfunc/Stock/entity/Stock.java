package com.bisfunc.Stock.entity;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Data
@TableName("stock")
public class Stock {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Integer updateUser;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @TableField(fill = FieldFill.INSERT)
    private Integer deleteFlg;

    private String stock;

    public static String stockMapToJsonString(Map<String, Integer> stockMap) {
        return JSON.toJSONString(stockMap);
    }

    public static Map<String, Integer> jsonStringToStockMap(String jsonString) {
        return (jsonString != null && jsonString.length() > 0) ? JSON.parseObject(jsonString, HashMap.class) : new HashMap<>();
    }
}
