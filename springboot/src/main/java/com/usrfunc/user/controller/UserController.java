package com.usrfunc.user.controller;

import com.common.constants.MsgConstants;
import com.common.domain.vo.Result;
import com.usrfunc.user.Service.IUserService;
import com.usrfunc.user.entity.User;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    IUserService userService;

    @PostMapping(value = "/createUser")
    @ResponseBody
    public Result<String> createUser(@RequestBody User user){
        userService.createUser(user);
        return Result.success(MsgConstants.NormalReturn.CREATE_USER);
    }

    @PostMapping(value = "/updateUser")
    @ResponseBody
    public Result<String> updateUser(@RequestBody User user){
        userService.updateUser(user);
        return Result.success(MsgConstants.NormalReturn.MODIFY_USER);
    }
}
