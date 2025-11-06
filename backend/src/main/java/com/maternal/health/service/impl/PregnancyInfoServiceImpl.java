package com.maternal.health.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.maternal.health.common.BusinessException;
import com.maternal.health.dto.AddPregnancyInfoDTO;
import com.maternal.health.entity.PregnancyInfo;
import com.maternal.health.mapper.PregnancyInfoMapper;
import com.maternal.health.service.PregnancyInfoService;
import com.maternal.health.utils.BeanCopyUtil;
import com.maternal.health.utils.PregnancyUtil;
import com.maternal.health.vo.PregnancyInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

/**
 * 孕期信息服务实现类
 * 功能：实现孕期信息管理相关的业务逻辑
 */
@Slf4j
@Service
public class PregnancyInfoServiceImpl implements PregnancyInfoService {

    @Autowired
    private PregnancyInfoMapper pregnancyInfoMapper;

    /**
     * 获取当前用户的孕期信息
     * 功能：获取当前登录用户的最新孕期信息，并计算相关字段
     */
    @Override
    public PregnancyInfoVO getCurrentPregnancyInfo() {
        Long userId = StpUtil.getLoginIdAsLong();

        // 查询最新的孕期信息（按创建时间倒序，取第一条）
        LambdaQueryWrapper<PregnancyInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(PregnancyInfo::getUserId, userId);
        wrapper.orderByDesc(PregnancyInfo::getCreateTime);
        wrapper.last("LIMIT 1");

        PregnancyInfo pregnancyInfo = pregnancyInfoMapper.selectOne(wrapper);

        if (pregnancyInfo == null) {
            return null;
        }

        // 转换为VO并添加计算字段
        return convertToVO(pregnancyInfo);
    }

    /**
     * 添加或更新孕期信息
     * 功能：如果ID存在则更新，不存在则新增
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOrUpdatePregnancyInfo(AddPregnancyInfoDTO addPregnancyInfoDTO) {
        Long userId = StpUtil.getLoginIdAsLong();

        PregnancyInfo pregnancyInfo;

        if (addPregnancyInfoDTO.getId() != null) {
            // 更新现有记录
            pregnancyInfo = pregnancyInfoMapper.selectById(addPregnancyInfoDTO.getId());
            if (pregnancyInfo == null) {
                throw new BusinessException("孕期信息不存在");
            }
            if (!pregnancyInfo.getUserId().equals(userId)) {
                throw new BusinessException("无权操作该孕期信息");
            }
            BeanCopyUtil.copyProperties(addPregnancyInfoDTO, pregnancyInfo);
        } else {
            // 新增记录
            pregnancyInfo = BeanCopyUtil.copyProperties(addPregnancyInfoDTO, PregnancyInfo.class);
            pregnancyInfo.setUserId(userId);
        }

        // 计算预产期和孕周
        LocalDate lastPeriodDate = pregnancyInfo.getLastPeriodDate();
        LocalDate dueDate = PregnancyUtil.calculateDueDate(lastPeriodDate);
        int currentWeek = PregnancyUtil.calculateCurrentWeek(lastPeriodDate);
        int pregnancyStatus = PregnancyUtil.calculatePregnancyStatus(currentWeek, dueDate);

        pregnancyInfo.setDueDate(dueDate);
        pregnancyInfo.setCurrentWeek(currentWeek);
        pregnancyInfo.setPregnancyStatus(pregnancyStatus);

        // 保存或更新
        if (addPregnancyInfoDTO.getId() != null) {
            pregnancyInfoMapper.updateById(pregnancyInfo);
            log.info("孕期信息更新成功：userId={}, pregnancyId={}", userId, pregnancyInfo.getId());
        } else {
            pregnancyInfoMapper.insert(pregnancyInfo);
            log.info("孕期信息添加成功：userId={}, pregnancyId={}", userId, pregnancyInfo.getId());
        }
    }

    /**
     * 更新孕期计算字段
     * 功能：根据末次月经日期更新预产期、孕周等计算字段
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateCalculatedFields(Long pregnancyId) {
        PregnancyInfo pregnancyInfo = pregnancyInfoMapper.selectById(pregnancyId);
        if (pregnancyInfo == null) {
            throw new BusinessException("孕期信息不存在");
        }

        LocalDate lastPeriodDate = pregnancyInfo.getLastPeriodDate();
        LocalDate dueDate = PregnancyUtil.calculateDueDate(lastPeriodDate);
        int currentWeek = PregnancyUtil.calculateCurrentWeek(lastPeriodDate);
        int pregnancyStatus = PregnancyUtil.calculatePregnancyStatus(currentWeek, dueDate);

        pregnancyInfo.setDueDate(dueDate);
        pregnancyInfo.setCurrentWeek(currentWeek);
        pregnancyInfo.setPregnancyStatus(pregnancyStatus);

        pregnancyInfoMapper.updateById(pregnancyInfo);
    }

    /**
     * 删除孕期信息
     * 功能：逻辑删除指定的孕期信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deletePregnancyInfo(Long pregnancyId) {
        Long userId = StpUtil.getLoginIdAsLong();

        PregnancyInfo pregnancyInfo = pregnancyInfoMapper.selectById(pregnancyId);
        if (pregnancyInfo == null) {
            throw new BusinessException("孕期信息不存在");
        }

        if (!pregnancyInfo.getUserId().equals(userId)) {
            throw new BusinessException("无权操作该孕期信息");
        }

        pregnancyInfoMapper.deleteById(pregnancyId);
        log.info("孕期信息删除成功：userId={}, pregnancyId={}", userId, pregnancyId);
    }

    /**
     * 转换为VO并添加计算字段
     * 功能：将实体类转换为VO，并添加计算字段和文字描述
     */
    private PregnancyInfoVO convertToVO(PregnancyInfo pregnancyInfo) {
        PregnancyInfoVO vo = BeanCopyUtil.copyProperties(pregnancyInfo, PregnancyInfoVO.class);

        // 计算字段
        int currentDay = PregnancyUtil.calculateCurrentDay(pregnancyInfo.getLastPeriodDate());
        int daysToDelivery = PregnancyUtil.calculateDaysToDelivery(pregnancyInfo.getDueDate());

        vo.setCurrentDay(currentDay);
        vo.setDaysToDelivery(daysToDelivery);

        // 文字描述
        vo.setPregnancyStatusText(PregnancyUtil.getPregnancyStatusText(pregnancyInfo.getPregnancyStatus()));
        vo.setPregnancyTypeText(PregnancyUtil.getPregnancyTypeText(pregnancyInfo.getPregnancyType()));
        vo.setRiskLevelText(PregnancyUtil.getRiskLevelText(pregnancyInfo.getRiskLevel()));

        return vo;
    }
}
