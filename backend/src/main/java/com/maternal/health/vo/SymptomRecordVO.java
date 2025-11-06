package com.maternal.health.vo;

import lombok.Data;

import java.time.LocalDate;

/**
 * 症状记录VO
 * 功能：用于返回症状记录数据
 */
@Data
public class SymptomRecordVO {

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 记录日期
     */
    private LocalDate recordDate;

    /**
     * 症状类型（1-头痛，2-恶心呕吐，3-腹痛，4-水肿，5-其他）
     */
    private Integer symptomType;

    /**
     * 症状类型文本
     */
    private String symptomTypeText;

    /**
     * 症状描述
     */
    private String symptomDesc;

    /**
     * 严重程度（1-轻微，2-中等，3-严重）
     */
    private Integer severity;

    /**
     * 严重程度文本
     */
    private String severityText;

    /**
     * 持续时长（分钟）
     */
    private Integer duration;

    /**
     * 是否就医（0-否，1-是）
     */
    private Integer seekMedical;

    /**
     * 是否就医文本
     */
    private String seekMedicalText;

    /**
     * 处理措施
     */
    private String treatment;

    /**
     * 备注
     */
    private String notes;
}
