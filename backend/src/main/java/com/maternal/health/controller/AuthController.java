package com.maternal.health.controller;

import com.maternal.health.result.R;
import com.maternal.health.dto.LoginDTO;
import com.maternal.health.dto.RegisterDTO;
import com.maternal.health.dto.WxLoginDTO;
import com.maternal.health.service.AuthService;
import com.maternal.health.vo.LoginVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 认证控制器
 * 功能：处理用户认证相关的HTTP请求（登录、注册、退出登录）
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    /**
     * 手机号密码登录
     * 功能：用户使用手机号和密码进行登录
     */
    @PostMapping("/login")
    public R<LoginVO> login(@Validated @RequestBody LoginDTO loginDTO) {
        LoginVO loginVO = authService.login(loginDTO);
        return R.ok(loginVO);
    }

    /**
     * 用户注册
     * 功能：注册新用户账号
     */
    @PostMapping("/register")
    public R<LoginVO> register(@Validated @RequestBody RegisterDTO registerDTO) {
        LoginVO loginVO = authService.register(registerDTO);
        return R.ok(loginVO);
    }

    /**
     * 微信登录
     * 功能：使用微信小程序code进行登录
     */
    @PostMapping("/wxLogin")
    public R<LoginVO> wxLogin(@Validated @RequestBody WxLoginDTO wxLoginDTO) {
        LoginVO loginVO = authService.wxLogin(wxLoginDTO);
        return R.ok(loginVO);
    }

    /**
     * 退出登录
     * 功能：用户退出登录，清除登录状态
     */
    @PostMapping("/logout")
    public R<Void> logout() {
        authService.logout();
        return R.ok("退出登录成功");
    }
}
