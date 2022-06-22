package com.usrfunc.user.entity.Vo;

import com.usrfunc.user.entity.User;
import lombok.Data;

@Data
public class LoginVo {

    private String token;

    private User user;
}
