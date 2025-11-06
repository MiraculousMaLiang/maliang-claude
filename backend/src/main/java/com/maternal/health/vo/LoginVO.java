package com.maternal.health.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 登录响应VO
 * 功能：返回给前端的登录结果，包含token和用户信息
 */
@Data
public class LoginVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 登录token
     */
    private String token;

    /**
     * 用户信息
     */
    private UserInfoVO userInfo;
}
