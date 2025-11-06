package com.maternal.health.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 体征监测实体类
 * 功能：存储用户的体征监测数据（血压、体重、体温等）
 */
@Data
@TableName("vital_signs")
public class VitalSigns {

    /**
     * 主键ID
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

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

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**
     * 逻辑删除标记（0-未删除，1-已删除）
     */
    @TableLogic
    private Integer deleted;
}
