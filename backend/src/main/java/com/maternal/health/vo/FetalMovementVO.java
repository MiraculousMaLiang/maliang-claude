package com.maternal.health.vo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * 胎动记录VO
 * 功能：返回给前端的胎动记录信息
 */
@Data
public class FetalMovementVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 胎动记录ID
     */
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 孕期信息ID
     */
    private Long pregnancyId;

    /**
     * 记录日期
     */
    private LocalDate recordDate;

    /**
     * 记录时间
     */
    private LocalTime recordTime;

    /**
     * 胎动次数
     */
    private Integer movementCount;

    /**
     * 时间段：1-早上，2-中午，3-晚上
     */
    private Integer timePeriod;

    /**
     * 时间段描述
     */
    private String timePeriodText;

    /**
     * 观察时长（分钟）
     */
    private Integer duration;

    /**
     * 胎动强度：1-轻微，2-适中，3-强烈
     */
    private Integer strength;

    /**
     * 胎动强度描述
     */
    private String strengthText;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}
