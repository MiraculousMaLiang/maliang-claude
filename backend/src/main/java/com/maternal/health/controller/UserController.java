package com.maternal.health.controller;

import com.maternal.health.result.R;
import com.maternal.health.dto.UpdateUserInfoDTO;
import com.maternal.health.service.UserService;
import com.maternal.health.vo.UserInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 用户控制器
 * 功能：处理用户信息管理相关的HTTP请求
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 获取当前登录用户信息
     * 功能：获取当前登录用户的详细信息
     */
    @GetMapping("/info")
    public R<UserInfoVO> getUserInfo() {
        UserInfoVO userInfo = userService.getCurrentUserInfo();
        return R.ok(userInfo);
    }

    /**
     * 更新用户信息
     * 功能：更新当前登录用户的基本信息
     */
    @PutMapping("/info")
    public R<Void> updateUserInfo(@Validated @RequestBody UpdateUserInfoDTO updateUserInfoDTO) {
        userService.updateUserInfo(updateUserInfoDTO);
        return R.ok("用户信息更新成功");
    }
}
