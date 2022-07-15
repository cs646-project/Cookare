package com.bisfunc.Plan.entity;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@TableName("plan")
@Data
public class Plan {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Integer updateUser;

    private String recipeIdList;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Integer updateTime;

    @TableField(fill = FieldFill.INSERT)
    private Integer deleteFlg;

    public static String planListToJsonString(List<Integer> recipeIdList) {
        return JSON.toJSONString(recipeIdList);
    }

    public static List<Integer> jsonStringToPlanList(String jsonString) {
        return (jsonString != null && jsonString.length() > 0) ? JSON.parseObject(jsonString, List.class) : new ArrayList<>();
    }

}
