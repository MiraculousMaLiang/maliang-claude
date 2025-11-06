package com.maternal.health.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.maternal.health.common.BusinessException;
import com.maternal.health.dto.AddEmergencyContactDTO;
import com.maternal.health.entity.EmergencyContact;
import com.maternal.health.mapper.EmergencyContactMapper;
import com.maternal.health.service.EmergencyContactService;
import com.maternal.health.utils.BeanCopyUtil;
import com.maternal.health.vo.EmergencyContactVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 紧急联系人服务实现类
 * 功能：实现紧急联系人管理相关的业务逻辑
 */
@Slf4j
@Service
public class EmergencyContactServiceImpl implements EmergencyContactService {

    @Autowired
    private EmergencyContactMapper emergencyContactMapper;

    /**
     * 获取当前用户的紧急联系人列表
     * 功能：查询当前登录用户的所有紧急联系人，按排序值升序排列
     */
    @Override
    public List<EmergencyContactVO> getCurrentUserContacts() {
        Long userId = StpUtil.getLoginIdAsLong();

        LambdaQueryWrapper<EmergencyContact> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(EmergencyContact::getUserId, userId);
        wrapper.orderByAsc(EmergencyContact::getSortOrder);
        wrapper.orderByDesc(EmergencyContact::getIsPrimary);

        List<EmergencyContact> contacts = emergencyContactMapper.selectList(wrapper);

        return contacts.stream()
                .map(contact -> BeanCopyUtil.copyProperties(contact, EmergencyContactVO.class))
                .collect(Collectors.toList());
    }

    /**
     * 添加紧急联系人
     * 功能：为当前用户添加新的紧急联系人
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addContact(AddEmergencyContactDTO addEmergencyContactDTO) {
        Long userId = StpUtil.getLoginIdAsLong();

        // 如果设置为主要联系人，需要先取消其他主要联系人
        if (addEmergencyContactDTO.getIsPrimary() != null && addEmergencyContactDTO.getIsPrimary() == 1) {
            cancelOtherPrimaryContact(userId);
        }

        // 创建新联系人
        EmergencyContact contact = BeanCopyUtil.copyProperties(addEmergencyContactDTO, EmergencyContact.class);
        contact.setUserId(userId);
        emergencyContactMapper.insert(contact);

        log.info("添加紧急联系人成功：userId={}, contactId={}", userId, contact.getId());
    }

    /**
     * 更新紧急联系人
     * 功能：更新指定的紧急联系人信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateContact(AddEmergencyContactDTO addEmergencyContactDTO) {
        Long userId = StpUtil.getLoginIdAsLong();

        if (addEmergencyContactDTO.getId() == null) {
            throw new BusinessException("联系人ID不能为空");
        }

        // 查询联系人是否存在
        EmergencyContact contact = emergencyContactMapper.selectById(addEmergencyContactDTO.getId());
        if (contact == null) {
            throw new BusinessException("联系人不存在");
        }

        // 验证联系人是否属于当前用户
        if (!contact.getUserId().equals(userId)) {
            throw new BusinessException("无权操作该联系人");
        }

        // 如果设置为主要联系人，需要先取消其他主要联系人
        if (addEmergencyContactDTO.getIsPrimary() != null && addEmergencyContactDTO.getIsPrimary() == 1) {
            cancelOtherPrimaryContact(userId);
        }

        // 更新联系人信息
        BeanCopyUtil.copyProperties(addEmergencyContactDTO, contact);
        emergencyContactMapper.updateById(contact);

        log.info("更新紧急联系人成功：userId={}, contactId={}", userId, contact.getId());
    }

    /**
     * 删除紧急联系人
     * 功能：逻辑删除指定的紧急联系人
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteContact(Long contactId) {
        Long userId = StpUtil.getLoginIdAsLong();

        // 查询联系人是否存在
        EmergencyContact contact = emergencyContactMapper.selectById(contactId);
        if (contact == null) {
            throw new BusinessException("联系人不存在");
        }

        // 验证联系人是否属于当前用户
        if (!contact.getUserId().equals(userId)) {
            throw new BusinessException("无权操作该联系人");
        }

        // 删除联系人
        emergencyContactMapper.deleteById(contactId);

        log.info("删除紧急联系人成功：userId={}, contactId={}", userId, contactId);
    }

    /**
     * 设置主要联系人
     * 功能：将指定联系人设置为主要联系人，其他联系人自动取消主要标记
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void setPrimaryContact(Long contactId) {
        Long userId = StpUtil.getLoginIdAsLong();

        // 查询联系人是否存在
        EmergencyContact contact = emergencyContactMapper.selectById(contactId);
        if (contact == null) {
            throw new BusinessException("联系人不存在");
        }

        // 验证联系人是否属于当前用户
        if (!contact.getUserId().equals(userId)) {
            throw new BusinessException("无权操作该联系人");
        }

        // 取消其他主要联系人
        cancelOtherPrimaryContact(userId);

        // 设置为主要联系人
        contact.setIsPrimary(1);
        emergencyContactMapper.updateById(contact);

        log.info("设置主要联系人成功：userId={}, contactId={}", userId, contactId);
    }

    /**
     * 取消其他主要联系人
     * 功能：将用户的所有联系人的主要标记设置为0
     */
    private void cancelOtherPrimaryContact(Long userId) {
        LambdaUpdateWrapper<EmergencyContact> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(EmergencyContact::getUserId, userId);
        updateWrapper.set(EmergencyContact::getIsPrimary, 0);
        emergencyContactMapper.update(null, updateWrapper);
    }
}
