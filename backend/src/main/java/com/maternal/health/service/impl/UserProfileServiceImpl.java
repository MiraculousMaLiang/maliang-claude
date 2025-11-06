package com.maternal.health.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.maternal.health.dto.UpdateUserProfileDTO;
import com.maternal.health.entity.UserProfile;
import com.maternal.health.mapper.UserProfileMapper;
import com.maternal.health.service.UserProfileService;
import com.maternal.health.utils.BeanCopyUtil;
import com.maternal.health.vo.UserProfileVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户档案服务实现类
 * 功能：实现用户健康档案管理相关的业务逻辑
 */
@Slf4j
@Service
public class UserProfileServiceImpl implements UserProfileService {

    @Autowired
    private UserProfileMapper userProfileMapper;

    /**
     * 获取当前用户档案
     * 功能：从Sa-Token获取当前登录用户ID，然后查询档案信息
     */
    @Override
    public UserProfileVO getCurrentUserProfile() {
        Long userId = StpUtil.getLoginIdAsLong();
        return getUserProfileByUserId(userId);
    }

    /**
     * 根据用户ID获取用户档案
     * 功能：根据用户ID查询健康档案信息，如果不存在返回null
     */
    @Override
    public UserProfileVO getUserProfileByUserId(Long userId) {
        LambdaQueryWrapper<UserProfile> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserProfile::getUserId, userId);
        UserProfile userProfile = userProfileMapper.selectOne(wrapper);

        if (userProfile == null) {
            return null;
        }

        return BeanCopyUtil.copyProperties(userProfile, UserProfileVO.class);
    }

    /**
     * 更新或创建用户档案
     * 功能：如果档案不存在则创建，存在则更新
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOrUpdateUserProfile(UpdateUserProfileDTO updateUserProfileDTO) {
        Long userId = StpUtil.getLoginIdAsLong();

        // 查询档案是否存在
        LambdaQueryWrapper<UserProfile> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserProfile::getUserId, userId);
        UserProfile userProfile = userProfileMapper.selectOne(wrapper);

        if (userProfile == null) {
            // 档案不存在，创建新档案
            userProfile = BeanCopyUtil.copyProperties(updateUserProfileDTO, UserProfile.class);
            userProfile.setUserId(userId);
            userProfileMapper.insert(userProfile);
            log.info("用户档案创建成功：userId={}", userId);
        } else {
            // 档案存在，更新档案
            BeanCopyUtil.copyProperties(updateUserProfileDTO, userProfile);
            userProfileMapper.updateById(userProfile);
            log.info("用户档案更新成功：userId={}", userId);
        }
    }
}
