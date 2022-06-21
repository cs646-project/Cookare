package com.usrfunc.login.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.usrfunc.login.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component(value = "userMapper")
public interface UserMapper extends BaseMapper<User> {

}
