package com.maternal.health.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 更新用户档案DTO
 * 功能：接收用户更新健康档案时提交的数据
 */
@Data
public class UpdateUserProfileDTO implements Serializable {

    private static final long serialVersionUID = 1L;

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
}
