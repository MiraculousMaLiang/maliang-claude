package com.maternal.health.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * 添加/更新孕期信息DTO
 * 功能：接收添加或更新孕期信息时提交的数据
 */
@Data
public class AddPregnancyInfoDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 孕期信息ID（更新时需要）
     */
    private Long id;

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
    @NotNull(message = "末次月经日期不能为空")
    private LocalDate lastPeriodDate;

    /**
     * 妊娠类型：1-单胎，2-双胎，3-多胎
     */
    private Integer pregnancyType;

    /**
     * 受孕方式：1-自然受孕，2-辅助生殖
     */
    private Integer conceptionMethod;

    /**
     * 风险等级：1-低危，2-高危
     */
    private Integer riskLevel;

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
}
