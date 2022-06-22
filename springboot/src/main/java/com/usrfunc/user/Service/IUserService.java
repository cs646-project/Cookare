package com.usrfunc.user.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.usrfunc.user.entity.User;

public interface IUserService extends IService<User> {
    /**
     * 根据用户名获取用户信息
     *
     * @param username
     * @return
     */
    User getUserInfoByUsername(String username);

    User getUserInfoByPhone(String phone);

    User getUserInfoByEmail(String email);

    int createUser(User user);

    boolean isValidUserInfo(User user);

    int updateUser(User user);
}
