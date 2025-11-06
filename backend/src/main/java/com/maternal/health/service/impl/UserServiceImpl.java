package com.maternal.health.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.maternal.health.common.BusinessException;
import com.maternal.health.dto.UpdateUserInfoDTO;
import com.maternal.health.entity.SysUser;
import com.maternal.health.mapper.SysUserMapper;
import com.maternal.health.service.UserService;
import com.maternal.health.utils.BeanCopyUtil;
import com.maternal.health.vo.UserInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户服务实现类
 * 功能：实现用户信息管理相关的业务逻辑
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    /**
     * 获取当前登录用户信息
     * 功能：从Sa-Token获取当前登录用户ID，然后查询用户信息
     */
    @Override
    public UserInfoVO getCurrentUserInfo() {
        Long userId = StpUtil.getLoginIdAsLong();
        return getUserInfoById(userId);
    }

    /**
     * 根据用户ID获取用户信息
     * 功能：根据用户ID查询用户详细信息
     */
    @Override
    public UserInfoVO getUserInfoById(Long userId) {
        SysUser user = sysUserMapper.selectById(userId);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        return BeanCopyUtil.copyProperties(user, UserInfoVO.class);
    }

    /**
     * 更新用户信息
     * 功能：更新当前登录用户的基本信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUserInfo(UpdateUserInfoDTO updateUserInfoDTO) {
        Long userId = StpUtil.getLoginIdAsLong();

        // 查询用户是否存在
        SysUser user = sysUserMapper.selectById(userId);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }

        // 拷贝属性
        BeanCopyUtil.copyProperties(updateUserInfoDTO, user);

        // 更新用户信息
        sysUserMapper.updateById(user);

        log.info("用户信息更新成功：userId={}", userId);
    }
}
