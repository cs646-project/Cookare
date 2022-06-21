package com.usrfunc.login.entity.Vo;

import com.usrfunc.login.entity.User;
import lombok.Data;

@Data
public class LoginVo {

    private String token;

    private User user;
}
