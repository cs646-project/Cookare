package com.usrfunc.login.entity.dto;

import lombok.Data;

@Data
public class LoginInputDto {
    private Integer method; // In Constants.LoginMethod, email 0, phone 1

    private String loginCard; //email or phone, depend on method field

    private String password;
}
