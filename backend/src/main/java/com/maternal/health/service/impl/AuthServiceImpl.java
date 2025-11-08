package com.maternal.health.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.maternal.health.common.BusinessException;
import com.maternal.health.dto.LoginDTO;
import com.maternal.health.dto.RegisterDTO;
import com.maternal.health.dto.WxLoginDTO;
import com.maternal.health.entity.SysUser;
import com.maternal.health.mapper.SysUserMapper;
import com.maternal.health.service.AuthService;
import com.maternal.health.utils.BeanCopyUtil;
import com.maternal.health.utils.PasswordUtil;
import com.maternal.health.utils.WechatUtil;
import com.maternal.health.vo.LoginVO;
import com.maternal.health.vo.UserInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 认证服务实现类
 * 功能：实现用户认证相关的业务逻辑
 */
@Slf4j
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private WechatUtil wechatUtil;

    /**
     * 手机号密码登录
     * 功能：验证用户手机号和密码，登录成功后返回token和用户信息
     */
    @Override
    public LoginVO login(LoginDTO loginDTO) {
        // 根据手机号查询用户
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getPhone, loginDTO.getPhone());
        SysUser user = sysUserMapper.selectOne(wrapper);

        // 验证用户是否存在
        if (user == null) {
            throw new BusinessException("用户不存在");
        }

        // 验证密码
        if (!PasswordUtil.verify(loginDTO.getPassword(), user.getPassword())) {
            throw new BusinessException("密码错误");
        }

        // 验证用户状态
        if (user.getStatus() == 0) {
            throw new BusinessException("账号已被禁用");
        }

        // 登录成功，生成token
        StpUtil.login(user.getId());
        String token = StpUtil.getTokenValue();

        // 构造返回结果
        LoginVO loginVO = new LoginVO();
        loginVO.setToken(token);
        loginVO.setUserInfo(BeanCopyUtil.copyProperties(user, UserInfoVO.class));

        log.info("用户登录成功：userId={}, phone={}", user.getId(), user.getPhone());
        return loginVO;
    }

    /**
     * 用户注册
     * 功能：注册新用户，自动进行登录并返回token和用户信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public LoginVO register(RegisterDTO registerDTO) {
        // 检查手机号是否已注册
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getPhone, registerDTO.getPhone());
        Long count = sysUserMapper.selectCount(wrapper);
        if (count > 0) {
            throw new BusinessException("该手机号已注册");
        }

        // 创建新用户
        SysUser user = new SysUser();
        user.setPhone(registerDTO.getPhone());
        user.setPassword(PasswordUtil.encrypt(registerDTO.getPassword()));
        user.setNickname(registerDTO.getNickname());
        user.setGender(registerDTO.getGender() != null ? registerDTO.getGender() : 2);
        user.setStatus(1);

        // 保存用户
        sysUserMapper.insert(user);

        // 自动登录
        StpUtil.login(user.getId());
        String token = StpUtil.getTokenValue();

        // 构造返回结果
        LoginVO loginVO = new LoginVO();
        loginVO.setToken(token);
        loginVO.setUserInfo(BeanCopyUtil.copyProperties(user, UserInfoVO.class));

        log.info("用户注册成功：userId={}, phone={}", user.getId(), user.getPhone());
        return loginVO;
    }

    /**
     * 微信登录
     * 功能：使用微信code进行登录，如果是新用户则自动注册
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public LoginVO wxLogin(WxLoginDTO wxLoginDTO) {
        // 调用微信接口获取openid和session_key
        WechatUtil.WxSessionInfo sessionInfo = wechatUtil.code2Session(wxLoginDTO.getCode());

        if (sessionInfo == null || StrUtil.isBlank(sessionInfo.getOpenid())) {
            throw new BusinessException("微信登录失败，请重试");
        }

        String openid = sessionInfo.getOpenid();
        String unionid = sessionInfo.getUnionid();

        // 根据openid查询用户
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getOpenid, openid);
        SysUser user = sysUserMapper.selectOne(wrapper);

        // 如果用户不存在，自动注册
        if (user == null) {
            user = new SysUser();
            user.setOpenid(openid);
            user.setUnionid(unionid);
            user.setNickname(StrUtil.isNotBlank(wxLoginDTO.getNickname()) ? wxLoginDTO.getNickname() : "用户" + System.currentTimeMillis());
            user.setAvatar(wxLoginDTO.getAvatar());
            user.setGender(wxLoginDTO.getGender() != null ? wxLoginDTO.getGender() : 2);
            user.setStatus(1);
            sysUserMapper.insert(user);
            log.info("微信用户自动注册：userId={}, openid={}", user.getId(), openid);
        } else {
            // 如果用户已存在，更新unionid（如果有）
            if (StrUtil.isNotBlank(unionid) && !unionid.equals(user.getUnionid())) {
                user.setUnionid(unionid);
                sysUserMapper.updateById(user);
            }
            // 如果提供了昵称和头像，也更新
            boolean needUpdate = false;
            if (StrUtil.isNotBlank(wxLoginDTO.getNickname()) && !wxLoginDTO.getNickname().equals(user.getNickname())) {
                user.setNickname(wxLoginDTO.getNickname());
                needUpdate = true;
            }
            if (StrUtil.isNotBlank(wxLoginDTO.getAvatar()) && !wxLoginDTO.getAvatar().equals(user.getAvatar())) {
                user.setAvatar(wxLoginDTO.getAvatar());
                needUpdate = true;
            }
            if (needUpdate) {
                sysUserMapper.updateById(user);
            }
        }

        // 验证用户状态
        if (user.getStatus() == 0) {
            throw new BusinessException("账号已被禁用");
        }

        // 登录成功，生成token
        StpUtil.login(user.getId());
        String token = StpUtil.getTokenValue();

        // 构造返回结果
        LoginVO loginVO = new LoginVO();
        loginVO.setToken(token);
        loginVO.setUserInfo(BeanCopyUtil.copyProperties(user, UserInfoVO.class));

        log.info("微信登录成功：userId={}, openid={}", user.getId(), openid);
        return loginVO;
    }

    /**
     * 退出登录
     * 功能：清除当前用户的登录状态
     */
    @Override
    public void logout() {
        Long userId = StpUtil.getLoginIdAsLong();
        StpUtil.logout();
        log.info("用户退出登录：userId={}", userId);
    }
}
