package com.example.demo.User.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

//使用lombok注解简化了很多重复代码，比如自动生成getter和setter。
@TableName("user") //与数据库的表名对应
@Data
public class User {
    @TableId(value = "id", type = IdType.AUTO) //定义主键且自增
    private Integer id;
    private String username;
    private String password;
    private String nickName;
    private Integer age;
    private String sex;
    private String address;
}
