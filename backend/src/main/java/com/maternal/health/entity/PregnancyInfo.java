package com.maternal.health.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 孕期信息实体类
 * 功能：对应pregnancy_info表，存储孕妇的孕期相关信息
 */
@Data
@TableName("pregnancy_info")
public class PregnancyInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 孕期信息ID（主键）
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 用户ID
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 怀孕次数
     */
    @TableField("pregnancy_count")
    private Integer pregnancyCount;

    /**
     * 生育次数
     */
    @TableField("birth_count")
    private Integer birthCount;

    /**
     * 末次月经日期
     */
    @TableField("last_period_date")
    private LocalDate lastPeriodDate;

    /**
     * 预产期
     */
    @TableField("due_date")
    private LocalDate dueDate;

    /**
     * 当前孕周
     */
    @TableField("current_week")
    private Integer currentWeek;

    /**
     * 孕期状态：1-孕早期，2-孕中期，3-孕晚期，4-已分娩
     */
    @TableField("pregnancy_status")
    private Integer pregnancyStatus;

    /**
     * 妊娠类型：1-单胎，2-双胎，3-多胎
     */
    @TableField("pregnancy_type")
    private Integer pregnancyType;

    /**
     * 受孕方式：1-自然受孕，2-辅助生殖
     */
    @TableField("conception_method")
    private Integer conceptionMethod;

    /**
     * 风险等级：1-低危，2-高危
     */
    @TableField("risk_level")
    private Integer riskLevel;

    /**
     * 建档医院名称
     */
    @TableField("hospital_name")
    private String hospitalName;

    /**
     * 主治医生姓名
     */
    @TableField("doctor_name")
    private String doctorName;

    /**
     * 医生联系电话
     */
    @TableField("doctor_phone")
    private String doctorPhone;

    /**
     * 备注信息
     */
    @TableField("remarks")
    private String remarks;

    /**
     * 删除标记：0-未删除，1-已删除
     */
    @TableField("deleted")
    @TableLogic
    private Integer deleted;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField("update_time")
    private LocalDateTime updateTime;
}
