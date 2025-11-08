package com.maternal.health.controller;

import com.maternal.health.result.R;
import com.maternal.health.dto.AddEmergencyContactDTO;
import com.maternal.health.service.EmergencyContactService;
import com.maternal.health.vo.EmergencyContactVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 紧急联系人控制器
 * 功能：处理紧急联系人管理相关的HTTP请求
 */
@RestController
@RequestMapping("/user/emergency-contact")
public class EmergencyContactController {

    @Autowired
    private EmergencyContactService emergencyContactService;

    /**
     * 获取当前用户的紧急联系人列表
     * 功能：查询当前登录用户的所有紧急联系人
     */
    @GetMapping("/list")
    public R<List<EmergencyContactVO>> getContactList() {
        List<EmergencyContactVO> contacts = emergencyContactService.getCurrentUserContacts();
        return R.ok(contacts);
    }

    /**
     * 添加紧急联系人
     * 功能：为当前用户添加新的紧急联系人
     */
    @PostMapping
    public R<Void> addContact(@Validated @RequestBody AddEmergencyContactDTO addEmergencyContactDTO) {
        emergencyContactService.addContact(addEmergencyContactDTO);
        return R.ok("添加紧急联系人成功");
    }

    /**
     * 更新紧急联系人
     * 功能：更新指定的紧急联系人信息
     */
    @PutMapping
    public R<Void> updateContact(@Validated @RequestBody AddEmergencyContactDTO addEmergencyContactDTO) {
        emergencyContactService.updateContact(addEmergencyContactDTO);
        return R.ok("更新紧急联系人成功");
    }

    /**
     * 删除紧急联系人
     * 功能：删除指定的紧急联系人
     */
    @DeleteMapping("/{contactId}")
    public R<Void> deleteContact(@PathVariable Long contactId) {
        emergencyContactService.deleteContact(contactId);
        return R.ok("删除紧急联系人成功");
    }

    /**
     * 设置主要联系人
     * 功能：将指定联系人设置为主要联系人
     */
    @PutMapping("/{contactId}/primary")
    public R<Void> setPrimaryContact(@PathVariable Long contactId) {
        emergencyContactService.setPrimaryContact(contactId);
        return R.ok("设置主要联系人成功");
    }
}
