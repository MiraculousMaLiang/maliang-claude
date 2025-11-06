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
import java.time.LocalTime;

/**
 * 胎动记录实体类
 * 功能：对应fetal_movement表，记录胎动情况
 */
@Data
@TableName("fetal_movement")
public class FetalMovement implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 胎动记录ID（主键）
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 用户ID
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 孕期信息ID
     */
    @TableField("pregnancy_id")
    private Long pregnancyId;

    /**
     * 记录日期
     */
    @TableField("record_date")
    private LocalDate recordDate;

    /**
     * 记录时间
     */
    @TableField("record_time")
    private LocalTime recordTime;

    /**
     * 胎动次数
     */
    @TableField("movement_count")
    private Integer movementCount;

    /**
     * 时间段：1-早上，2-中午，3-晚上
     */
    @TableField("time_period")
    private Integer timePeriod;

    /**
     * 观察时长（分钟）
     */
    @TableField("duration")
    private Integer duration;

    /**
     * 胎动强度：1-轻微，2-适中，3-强烈
     */
    @TableField("strength")
    private Integer strength;

    /**
     * 备注
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
