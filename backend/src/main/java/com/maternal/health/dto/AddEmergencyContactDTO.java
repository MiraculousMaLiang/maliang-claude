package com.maternal.health.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 添加紧急联系人DTO
 * 功能：接收添加或更新紧急联系人时提交的数据
 */
@Data
public class AddEmergencyContactDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 联系人ID（更新时需要）
     */
    private Long id;

    /**
     * 联系人姓名
     */
    @NotBlank(message = "联系人姓名不能为空")
    private String contactName;

    /**
     * 关系（如：配偶、父母、朋友等）
     */
    @NotBlank(message = "关系不能为空")
    private String relationship;

    /**
     * 联系电话
     */
    @NotBlank(message = "联系电话不能为空")
    private String phone;

    /**
     * 是否主要联系人：0-否，1-是
     */
    private Integer isPrimary;

    /**
     * 排序值
     */
    private Integer sortOrder;
}
