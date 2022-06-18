package com.example.demo.User.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.User.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Component(value = "userMapper")
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
