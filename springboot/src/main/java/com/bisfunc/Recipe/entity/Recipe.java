package com.bisfunc.Recipe.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

@Data
@TableName("recipe")
public class Recipe {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String title;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Integer updateUser;

    private String content;

    private String tags;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    private Integer deleteFlg;

    private Integer likesNum;

    private Integer collectNum;

    private Integer commentNum;

    private String coverUrl;
}
