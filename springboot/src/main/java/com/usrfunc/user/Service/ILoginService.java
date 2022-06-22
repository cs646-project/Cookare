package com.usrfunc.user.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.usrfunc.user.entity.User;

public interface ILoginService extends IService<User> {

    /**
     * get current login user's userID
     *
     * @return
     */
    Integer getCurrentUserId();
}
