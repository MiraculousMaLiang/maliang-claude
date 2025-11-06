package com.maternal.health.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 体征监测VO
 * 功能：用于返回体征监测记录数据
 */
@Data
public class VitalSignsVO {

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 记录日期
     */
    private LocalDate recordDate;

    /**
     * 收缩压(mmHg)
     */
    private Integer systolicPressure;

    /**
     * 舒张压(mmHg)
     */
    private Integer diastolicPressure;

    /**
     * 血压状态文本（正常/偏高/偏低）
     */
    private String bloodPressureStatus;

    /**
     * 体重(kg)
     */
    private BigDecimal weight;

    /**
     * 体重变化（相比上次记录）
     */
    private BigDecimal weightChange;

    /**
     * 体温(℃)
     */
    private BigDecimal temperature;

    /**
     * 体温状态文本（正常/偏高/偏低）
     */
    private String temperatureStatus;

    /**
     * 心率(次/分钟)
     */
    private Integer heartRate;

    /**
     * 心率状态文本（正常/偏快/偏慢）
     */
    private String heartRateStatus;

    /**
     * 血糖(mmol/L)
     */
    private BigDecimal bloodSugar;

    /**
     * 血糖状态文本（正常/偏高/偏低）
     */
    private String bloodSugarStatus;

    /**
     * 备注
     */
    private String notes;
}
