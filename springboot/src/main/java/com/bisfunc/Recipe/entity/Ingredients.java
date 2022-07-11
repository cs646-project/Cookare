package com.bisfunc.Recipe.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("ingredients")
public class Ingredients {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String name;

    private Integer recipeId;

    private Integer num;

    private String quantifier;
}
