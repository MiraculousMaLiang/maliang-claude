package com.maternal.health.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 用户档案VO
 * 功能：返回给前端的用户健康档案信息
 */
@Data
public class UserProfileVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 档案ID
     */
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 身高（厘米）
     */
    private BigDecimal height;

    /**
     * 体重（千克）
     */
    private BigDecimal weight;

    /**
     * 血型：A/B/O/AB/RH阴性等
     */
    private String bloodType;

    /**
     * 婚姻状况：0-未婚，1-已婚，2-离异，3-丧偶
     */
    private Integer marriageStatus;

    /**
     * 学历
     */
    private String education;

    /**
     * 职业
     */
    private String occupation;

    /**
     * 既往病史
     */
    private String medicalHistory;

    /**
     * 过敏史
     */
    private String allergyHistory;

    /**
     * 家族病史
     */
    private String familyHistory;

    /**
     * 手术史
     */
    private String surgeryHistory;

    /**
     * 居住地址
     */
    private String address;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
