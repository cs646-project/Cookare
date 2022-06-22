package com.usrfunc.user.Service;

import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.common.constants.Constants;
import com.common.constants.MsgConstants;
import com.common.exceptions.CkException;
import com.usrfunc.user.entity.User;
import com.usrfunc.user.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
@Component
public class UserService extends ServiceImpl<UserMapper, User> implements IUserService {
    @Resource
    UserMapper userMapper;

    @Resource
    ILoginService loginService;

    /**
     * @param username
     * @return
     */
    public User getUserInfoByUsername(String username) {
        return userMapper.selectOne(new LambdaQueryWrapper<User>()
                .eq(User::getUsername, username)
                .eq(User::getDeleteFlg, Constants.DELETE_FLG_FALSE));
    }

    @Override
    public User getUserInfoByPhone(String phone) {
        return userMapper.selectOne(new LambdaQueryWrapper<User>()
                .eq(User::getPhone, phone)
                .eq(User::getDeleteFlg, Constants.DELETE_FLG_FALSE));
    }

    @Override
    public User getUserInfoByEmail(String email) {
        return userMapper.selectOne(new LambdaQueryWrapper<User>()
                .eq(User::getEmail, email)
                .eq(User::getDeleteFlg, Constants.DELETE_FLG_FALSE));
    }

    public int createUser(User user) {
        // 检查是否包含必要信息
        if (!isValidUserInfo(user)) {
            throw new CkException(MsgConstants.ERROR.MISSING_INFO);
        }
        //查重
        checkDuplicateUserInfo(user);

        //用户添加
        int ret = userMapper.insert(user);

        // sa-token 登录
        StpUtil.login(user.getId());
        // 获取session
        SaSession session = StpUtil.getSession();
        // 设置用户信息
        session.set(Constants.SESSION_USER_KEY, user);


        //业务相关初始化

        //logOut
        StpUtil.logout();

        return ret;
    }

    @Override
    public boolean isValidUserInfo(User user) {
        if (user.getUsername() == null || user.getUsername().isEmpty()) {
            return false;
        }
        if (user.getPassword() == null || user.getPassword().length() < 4) {
            return false;
        }
        if (user.getPhone() == null && user.getEmail() == null) {
            return false;
        }
        return true;
    }

    @Override
    public int updateUser(User user) {
        Integer currentUserId = loginService.getCurrentUserId();
        // 待更新用户与当前登录用户信息不一致
        if (user.getId() == null || !user.getId().equals(currentUserId)) {
            throw new CkException(MsgConstants.ERROR.WRONG_USER);
        }
        //查重
        checkDuplicateUserInfo(user);
        return userMapper.updateById(user);
    }

    private void checkDuplicateUserInfo(User user) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", user.getUsername()).or()
                .eq("phone", user.getPhone()).or()
                .eq("email", user.getEmail());
        List<User> userTmpList = userMapper.selectList(queryWrapper);
        if (!userTmpList.isEmpty()) {
            throw new CkException(MsgConstants.ERROR.DUPLICATE_INFO);
        }
    }
}
