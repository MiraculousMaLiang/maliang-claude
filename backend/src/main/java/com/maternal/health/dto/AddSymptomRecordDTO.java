package com.maternal.health.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * 添加症状记录DTO
 * 功能：用于接收添加症状记录的请求参数
 */
@Data
public class AddSymptomRecordDTO {

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
     * 症状类型（1-头痛，2-恶心呕吐，3-腹痛，4-水肿，5-其他）
     */
    @NotNull(message = "症状类型不能为空")
    private Integer symptomType;

    /**
     * 症状描述
     */
    @NotNull(message = "症状描述不能为空")
    private String symptomDesc;

    /**
     * 严重程度（1-轻微，2-中等，3-严重）
     */
    @NotNull(message = "严重程度不能为空")
    private Integer severity;

    /**
     * 持续时长（分钟）
     */
    private Integer duration;

    /**
     * 是否就医（0-否，1-是）
     */
    private Integer seekMedical;

    /**
     * 处理措施
     */
    private String treatment;

    /**
     * 备注
     */
    private String notes;
}
