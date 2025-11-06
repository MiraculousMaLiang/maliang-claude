package com.maternal.health.service;

import com.maternal.health.dto.UpdateUserProfileDTO;
import com.maternal.health.vo.UserProfileVO;

/**
 * 用户档案服务接口
 * 功能：提供用户健康档案管理相关的业务方法
 */
public interface UserProfileService {

    /**
     * 获取当前用户档案
     * 功能：获取当前登录用户的健康档案信息
     */
    UserProfileVO getCurrentUserProfile();

    /**
     * 根据用户ID获取用户档案
     * 功能：根据用户ID查询健康档案信息
     */
    UserProfileVO getUserProfileByUserId(Long userId);

    /**
     * 更新或创建用户档案
     * 功能：如果档案不存在则创建，存在则更新
     */
    void saveOrUpdateUserProfile(UpdateUserProfileDTO updateUserProfileDTO);
}
