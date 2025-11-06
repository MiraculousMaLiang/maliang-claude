package com.maternal.health.service;

import com.maternal.health.dto.AddEmergencyContactDTO;
import com.maternal.health.vo.EmergencyContactVO;

import java.util.List;

/**
 * 紧急联系人服务接口
 * 功能：提供紧急联系人管理相关的业务方法
 */
public interface EmergencyContactService {

    /**
     * 获取当前用户的紧急联系人列表
     * 功能：查询当前登录用户的所有紧急联系人
     */
    List<EmergencyContactVO> getCurrentUserContacts();

    /**
     * 添加紧急联系人
     * 功能：为当前用户添加新的紧急联系人
     */
    void addContact(AddEmergencyContactDTO addEmergencyContactDTO);

    /**
     * 更新紧急联系人
     * 功能：更新指定的紧急联系人信息
     */
    void updateContact(AddEmergencyContactDTO addEmergencyContactDTO);

    /**
     * 删除紧急联系人
     * 功能：删除指定的紧急联系人
     */
    void deleteContact(Long contactId);

    /**
     * 设置主要联系人
     * 功能：将指定联系人设置为主要联系人
     */
    void setPrimaryContact(Long contactId);
}
