package com.maternal.health.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 症状记录实体类
 * 功能：存储用户的症状记录（不适症状、严重程度等）
 */
@Data
@TableName("symptom_record")
public class SymptomRecord {

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
     * 症状类型（1-头痛，2-恶心呕吐，3-腹痛，4-水肿，5-其他）
     */
    private Integer symptomType;

    /**
     * 症状描述
     */
    private String symptomDesc;

    /**
     * 严重程度（1-轻微，2-中等，3-严重）
     */
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
