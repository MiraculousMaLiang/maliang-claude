package com.maternal.health.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 紧急联系人实体类
 * 功能：对应emergency_contact表，存储用户的紧急联系人信息
 */
@Data
@TableName("emergency_contact")
public class EmergencyContact implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 联系人ID（主键）
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 用户ID
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 联系人姓名
     */
    @TableField("contact_name")
    private String contactName;

    /**
     * 关系（如：配偶、父母、朋友等）
     */
    @TableField("relationship")
    private String relationship;

    /**
     * 联系电话
     */
    @TableField("phone")
    private String phone;

    /**
     * 是否主要联系人：0-否，1-是
     */
    @TableField("is_primary")
    private Integer isPrimary;

    /**
     * 排序值
     */
    @TableField("sort_order")
    private Integer sortOrder;

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
