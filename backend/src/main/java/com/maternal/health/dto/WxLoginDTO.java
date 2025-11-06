package com.maternal.health.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 微信登录请求DTO
 * 功能：接收微信小程序登录时的code
 */
@Data
public class WxLoginDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 微信登录code
     */
    @NotBlank(message = "微信登录code不能为空")
    private String code;

    /**
     * 昵称（可选）
     */
    private String nickname;

    /**
     * 头像（可选）
     */
    private String avatar;

    /**
     * 性别（可选）：0-未知，1-男，2-女
     */
    private Integer gender;
}
