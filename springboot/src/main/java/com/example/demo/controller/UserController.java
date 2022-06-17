package com.example.demo.controller;

import com.example.demo.common.Result;
import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController //第一步，定义这是一个返回json的controller
@RequestMapping("/user") //第二部，确定接口的路由
public class UserController {

    @Resource
    UserMapper userMapper;

    @PostMapping //处理post请求
    public Result<?> save(@RequestBody User user){ //RequestBody，把收到的json转化成java对象
        userMapper.insert(user);
        return Result.success();
    }
}
