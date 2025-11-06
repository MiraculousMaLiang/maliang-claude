package com.maternal.health.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * 添加胎动记录DTO
 * 功能：接收添加胎动记录时提交的数据
 */
@Data
public class AddFetalMovementDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 记录日期
     */
    @NotNull(message = "记录日期不能为空")
    private LocalDate recordDate;

    /**
     * 记录时间
     */
    private LocalTime recordTime;

    /**
     * 胎动次数
     */
    @NotNull(message = "胎动次数不能为空")
    private Integer movementCount;

    /**
     * 时间段：1-早上，2-中午，3-晚上
     */
    @NotNull(message = "时间段不能为空")
    private Integer timePeriod;

    /**
     * 观察时长（分钟）
     */
    private Integer duration;

    /**
     * 胎动强度：1-轻微，2-适中，3-强烈
     */
    private Integer strength;

    /**
     * 备注
     */
    private String remarks;
}
