package com.usrfunc.user.Service;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.common.constants.Constants;
import com.usrfunc.user.entity.User;
import com.usrfunc.user.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Slf4j
@Service
@Component
public class LoginService extends ServiceImpl<UserMapper, User> implements ILoginService{
    @Resource
    UserMapper userMapper;

    @Override
    public Integer getCurrentUserId() {
        return StpUtil.getLoginIdAsInt();
    }
}
