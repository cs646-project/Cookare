package com.usrfunc.login.controller;

import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.common.constants.Constants;
import com.common.constants.MsgConstants;
import com.common.domain.vo.Result;
import com.usrfunc.login.Service.ILoginService;
import com.usrfunc.login.entity.User;
import com.usrfunc.login.entity.Vo.LoginVo;
import com.usrfunc.login.entity.dto.LoginInputDto;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/login")
public class LoginController {
    @Resource
    ILoginService loginService;

    @PostMapping("/login")
    @ResponseBody
    public Result<?> login(@RequestBody LoginInputDto loginInputDto) {
        Integer method = loginInputDto.getMethod();
        User user = null;
        if(method == Constants.LoginMethod.BY_EMAIL){
            user = loginService.getUserInfoByEmail(loginInputDto.getLoginCard());
        } else if (method == Constants.LoginMethod.BY_PHONE){
            user = loginService.getUserInfoByPhone(loginInputDto.getLoginCard());
        }
        if (user == null) {
            return Result.error(MsgConstants.CheckDto.INVALID_LOGIN_INFO);
        }
        if (user.getDeleteFlg().equals(Constants.DELETE_FLG_TRUE)) {
            return Result.error(MsgConstants.CheckDto.USER_NOT_EXIST);
        }

        if(!loginInputDto.getPassword().equals(user.getPassword())) {
            return Result.error(MsgConstants.CheckDto.WRONG_PASSWORD);
        }

        // sa-token 登录
        StpUtil.login(user.getId());
        // 获取token
        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
        // 获取session
        SaSession session = StpUtil.getSession();
        // 设置用户信息
        session.set(Constants.SESSION_USER_KEY, user);

        LoginVo loginVo = new LoginVo();
        loginVo.setToken(tokenInfo.getTokenValue());
        loginVo.setUser(user);
        return Result.success(loginVo);
    }

    @PostMapping("/logout")
    @ResponseBody
    public Result<String> logout() {
        StpUtil.logout();
        return Result.success(MsgConstants.Behavior.LOG_OUT);
    }
}
