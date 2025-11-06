package com.maternal.health.service;

import com.maternal.health.dto.LoginDTO;
import com.maternal.health.dto.RegisterDTO;
import com.maternal.health.dto.WxLoginDTO;
import com.maternal.health.vo.LoginVO;

/**
 * 认证服务接口
 * 功能：提供用户认证相关的业务方法（登录、注册、退出登录）
 */
public interface AuthService {

    /**
     * 手机号密码登录
     * 功能：用户使用手机号和密码进行登录
     */
    LoginVO login(LoginDTO loginDTO);

    /**
     * 用户注册
     * 功能：注册新用户账号
     */
    LoginVO register(RegisterDTO registerDTO);

    /**
     * 微信登录
     * 功能：使用微信小程序code进行登录
     */
    LoginVO wxLogin(WxLoginDTO wxLoginDTO);

    /**
     * 退出登录
     * 功能：用户退出登录，清除登录状态
     */
    void logout();
}
