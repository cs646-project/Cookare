package com.usrfunc.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.usrfunc.user.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component(value = "userMapper")
public interface UserMapper extends BaseMapper<User> {

}
