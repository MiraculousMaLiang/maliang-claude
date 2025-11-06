package com.maternal.health.vo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 孕期信息VO
 * 功能：返回给前端的孕期信息
 */
@Data
public class PregnancyInfoVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 孕期信息ID
     */
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 怀孕次数
     */
    private Integer pregnancyCount;

    /**
     * 生育次数
     */
    private Integer birthCount;

    /**
     * 末次月经日期
     */
    private LocalDate lastPeriodDate;

    /**
     * 预产期
     */
    private LocalDate dueDate;

    /**
     * 当前孕周
     */
    private Integer currentWeek;

    /**
     * 当前孕天（距离末次月经的天数）
     */
    private Integer currentDay;

    /**
     * 距离预产期天数
     */
    private Integer daysToDelivery;

    /**
     * 孕期状态：1-孕早期，2-孕中期，3-孕晚期，4-已分娩
     */
    private Integer pregnancyStatus;

    /**
     * 孕期状态描述
     */
    private String pregnancyStatusText;

    /**
     * 妊娠类型：1-单胎，2-双胎，3-多胎
     */
    private Integer pregnancyType;

    /**
     * 妊娠类型描述
     */
    private String pregnancyTypeText;

    /**
     * 受孕方式：1-自然受孕，2-辅助生殖
     */
    private Integer conceptionMethod;

    /**
     * 风险等级：1-低危，2-高危
     */
    private Integer riskLevel;

    /**
     * 风险等级描述
     */
    private String riskLevelText;

    /**
     * 建档医院名称
     */
    private String hospitalName;

    /**
     * 主治医生姓名
     */
    private String doctorName;

    /**
     * 医生联系电话
     */
    private String doctorPhone;

    /**
     * 备注信息
     */
    private String remarks;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
