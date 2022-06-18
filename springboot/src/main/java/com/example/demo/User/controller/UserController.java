package com.example.demo.User.controller;

import com.example.demo.common.Result;
import com.example.demo.User.entity.User;
import com.example.demo.User.mapper.UserMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController //第一步，定义这是一个返回json的controller
@RequestMapping("/user") //第二部，确定接口的路由
public class UserController {

    @Resource(name = "userMapper")
    UserMapper userMapper;

    @PostMapping //处理post请求
    public Result<?> save(@RequestBody User user){ //RequestBody，把收到的json转化成java对象
        userMapper.insert(user);
        return Result.success();
    }

    @PostMapping(value = "/test")
    @ResponseBody
    public Result<String> test(@RequestBody User user){
        return Result.success("okkkk");
    }
}
