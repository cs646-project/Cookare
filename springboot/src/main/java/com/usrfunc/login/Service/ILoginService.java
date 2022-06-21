package com.usrfunc.login.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.usrfunc.login.entity.User;

import java.util.List;

public interface ILoginService extends IService<User> {
    /**
     * 根据用户名获取用户信息
     *
     * @param username
     * @return
     */
    User getUserInfoByUsername(String username);

    User getUserInfoByPhone(String phone);

    User getUserInfoByEmail(String email);
    /**
     * get current login user's userID
     *
     * @return
     */
    Integer getCurrentUserId();
}
