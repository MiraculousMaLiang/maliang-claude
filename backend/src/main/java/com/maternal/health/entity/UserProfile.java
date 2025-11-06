package com.maternal.health.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 用户档案实体类
 * 功能：对应user_profile表，存储用户的健康档案信息
 */
@Data
@TableName("user_profile")
public class UserProfile implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 档案ID（主键）
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 用户ID
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 身高（厘米）
     */
    @TableField("height")
    private BigDecimal height;

    /**
     * 体重（千克）
     */
    @TableField("weight")
    private BigDecimal weight;

    /**
     * 血型：A/B/O/AB/RH阴性等
     */
    @TableField("blood_type")
    private String bloodType;

    /**
     * 婚姻状况：0-未婚，1-已婚，2-离异，3-丧偶
     */
    @TableField("marriage_status")
    private Integer marriageStatus;

    /**
     * 学历
     */
    @TableField("education")
    private String education;

    /**
     * 职业
     */
    @TableField("occupation")
    private String occupation;

    /**
     * 既往病史
     */
    @TableField("medical_history")
    private String medicalHistory;

    /**
     * 过敏史
     */
    @TableField("allergy_history")
    private String allergyHistory;

    /**
     * 家族病史
     */
    @TableField("family_history")
    private String familyHistory;

    /**
     * 手术史
     */
    @TableField("surgery_history")
    private String surgeryHistory;

    /**
     * 居住地址
     */
    @TableField("address")
    private String address;

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
