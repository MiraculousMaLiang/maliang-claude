package com.maternal.health.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * 更新用户信息DTO
 * 功能：接收用户更新基本信息时提交的数据
 */
@Data
public class UpdateUserInfoDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 头像URL
     */
    private String avatar;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 身份证号
     */
    private String idCard;

    /**
     * 性别：0-未知，1-男，2-女
     */
    private Integer gender;

    /**
     * 出生日期
     */
    private LocalDate birthday;
}
