package com.maternal.health.controller;

import com.maternal.health.common.Result;
import com.maternal.health.dto.UpdateUserProfileDTO;
import com.maternal.health.service.UserProfileService;
import com.maternal.health.vo.UserProfileVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 用户档案控制器
 * 功能：处理用户健康档案管理相关的HTTP请求
 */
@RestController
@RequestMapping("/user/profile")
public class UserProfileController {

    @Autowired
    private UserProfileService userProfileService;

    /**
     * 获取当前用户档案
     * 功能：获取当前登录用户的健康档案信息
     */
    @GetMapping
    public Result<UserProfileVO> getUserProfile() {
        UserProfileVO userProfile = userProfileService.getCurrentUserProfile();
        return Result.success(userProfile);
    }

    /**
     * 更新或创建用户档案
     * 功能：如果档案不存在则创建，存在则更新
     */
    @PutMapping
    public Result<Void> saveOrUpdateUserProfile(@Validated @RequestBody UpdateUserProfileDTO updateUserProfileDTO) {
        userProfileService.saveOrUpdateUserProfile(updateUserProfileDTO);
        return Result.success("用户档案保存成功");
    }
}
