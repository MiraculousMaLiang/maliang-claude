package com.maternal.health.vo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 紧急联系人VO
 * 功能：返回给前端的紧急联系人信息
 */
@Data
public class EmergencyContactVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 联系人ID
     */
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 联系人姓名
     */
    private String contactName;

    /**
     * 关系（如：配偶、父母、朋友等）
     */
    private String relationship;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 是否主要联系人：0-否，1-是
     */
    private Integer isPrimary;

    /**
     * 排序值
     */
    private Integer sortOrder;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}
