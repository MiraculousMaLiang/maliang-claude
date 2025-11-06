package com.maternal.health.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 添加体征监测DTO
 * 功能：用于接收添加体征监测记录的请求参数
 */
@Data
public class AddVitalSignsDTO {

    /**
     * 主键ID（更新时需要）
     */
    private Long id;

    /**
     * 记录日期
     */
    @NotNull(message = "记录日期不能为空")
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
     * 体重(kg)
     */
    private BigDecimal weight;

    /**
     * 体温(℃)
     */
    private BigDecimal temperature;

    /**
     * 心率(次/分钟)
     */
    private Integer heartRate;

    /**
     * 血糖(mmol/L)
     */
    private BigDecimal bloodSugar;

    /**
     * 备注
     */
    private String notes;
}
