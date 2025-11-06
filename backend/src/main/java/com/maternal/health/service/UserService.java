package com.maternal.health.service;

import com.maternal.health.dto.UpdateUserInfoDTO;
import com.maternal.health.vo.UserInfoVO;

/**
 * 用户服务接口
 * 功能：提供用户信息管理相关的业务方法
 */
public interface UserService {

    /**
     * 获取当前登录用户信息
     * 功能：根据token获取当前登录用户的详细信息
     */
    UserInfoVO getCurrentUserInfo();

    /**
     * 根据用户ID获取用户信息
     * 功能：根据用户ID查询用户信息
     */
    UserInfoVO getUserInfoById(Long userId);

    /**
     * 更新用户信息
     * 功能：更新当前登录用户的基本信息
     */
    void updateUserInfo(UpdateUserInfoDTO updateUserInfoDTO);
}
